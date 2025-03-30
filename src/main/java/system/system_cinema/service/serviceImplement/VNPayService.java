package system.system_cinema.service.serviceImplement;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import system.system_cinema.config.VNPAYConfig;
import system.system_cinema.dto.request.DetailsFvB;
import system.system_cinema.dto.request.LockSeatsRequest;
import system.system_cinema.constant.StatusSeat;
import system.system_cinema.domain.FoodOrder;
import system.system_cinema.domain.SeatBooking;
import system.system_cinema.domain.Ticket;
import system.system_cinema.repository.*;
import system.system_cinema.service.IVNPayService;
import system.system_cinema.utils.VNPayUtil;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j(topic = "VNPay-service")
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)

public class VNPayService implements IVNPayService {
    VNPAYConfig vnPayConfig;
    SeatRepository seatRepository;
    BookingRepository bookingRepository;
    ShowTimeRepository showTimeRepository;
    UserRepository userRepository;
    SnackRepository snackRepository;
    ComboRepository comboRepository;
    private RedisTemplate<String, String> redisTemplate;

    public boolean lockSeatAtomic(List<Integer> seatIds, int showtimeId, Long userId) {
        for (Integer seatId : seatIds) {
            String key = "seat:" + showtimeId + ":" + seatId;
            Boolean locked = redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(userId), Duration.ofMinutes(5));
            if (Boolean.FALSE.equals(locked)) {
                return false;
            }
        }
        return true;
    }
    @Override
    @Transactional
    public String handleOrder(HttpServletRequest request, LockSeatsRequest lockSeatsRequest) {
//    **************** Su dung khi bai toan co race condition lon: VD 1000 req ******************
//        try {
//            kafkaProducerService.SendMessageV2("seat-booking", "sb", objectMapper.writeValueAsString(lockSeatsRequest));
//            log.info("Send message success...................");
//            return String.valueOf(lockSeatsRequest.getUserId());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    **************** ******************************************************* ******************
        try {
            boolean locked = lockSeatAtomic(lockSeatsRequest.getSeatIds(), lockSeatsRequest.getShowtimeId(), (long) lockSeatsRequest.getUserId());
            if (locked){
                return HandleRequestBooking(request, lockSeatsRequest);
            } else {
                throw new RuntimeException("Invalid seat booking request");
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    // Xu ly request
    private String HandleRequestBooking(HttpServletRequest request, LockSeatsRequest lockSeatsRequest){
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        List<SeatBooking> seatBookings = new ArrayList<>();
        // Luu ghe
        for (Integer seatId : lockSeatsRequest.getSeatIds()) {
            SeatBooking seatBooking = SeatBooking
                    .builder()
                    .seat(seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found")))
                    .status(StatusSeat.OCCUPIED)
                    .build();
            seatBookings.add(seatBooking);
        }
        // Luu Food di kem
        List<FoodOrder> foodBeverageOrders = new ArrayList<>();
        if (lockSeatsRequest.getSnack() != null) {
            for (DetailsFvB d : lockSeatsRequest.getSnack()) {
                FoodOrder f = FoodOrder.builder()
                        .quantity(d.getQuantity())
                        .snack(snackRepository.findById(d.getId()).orElseThrow(() -> new RuntimeException("Snack not found")))
                        .build();
                foodBeverageOrders.add(f);
            }
        }
        // Luu combo di kem
        if (lockSeatsRequest.getCombo() != null) {
            for (DetailsFvB d : lockSeatsRequest.getCombo()) {
                FoodOrder f = FoodOrder.builder()
                        .quantity(d.getQuantity())
                        .combo(comboRepository.findById(d.getId()).orElseThrow(() -> new RuntimeException("Combo not found")))
                        .build();
                foodBeverageOrders.add(f);
            }
        }
        // Luu Order
        String idTicket = VNPayUtil.getRandomNumber(8);
        Ticket ticket = Ticket
                .builder()
                .id(Integer.parseInt(idTicket))
                .user(userRepository.findById(lockSeatsRequest.getUserId()).orElseThrow(() -> new RuntimeException("User not found")))
                .showtime(showTimeRepository.findById(lockSeatsRequest.getShowtimeId()).orElseThrow(() -> new RuntimeException("Showtime not found")))
                .price(Integer.parseInt(request.getParameter("amount")))
                .seatBookings(seatBookings)
                .build();
        if (!foodBeverageOrders.isEmpty()) {
            ticket.setFoodBeverageOrders(foodBeverageOrders);
            for (FoodOrder fb : foodBeverageOrders) {
                fb.setTicket(ticket);
            }
        }
        for (SeatBooking seatBooking : seatBookings) {
            seatBooking.setTicket(ticket);
        }

        bookingRepository.save(ticket);
        return CreateVNPAY(idTicket, amount, request);
    }

    // Xu ly VNPay
    private String CreateVNPAY(String idTicket, long amount, HttpServletRequest request){
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(idTicket);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true) + "&vnp_SecureHash=" + vnpSecureHash;
        return vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
    }
}
