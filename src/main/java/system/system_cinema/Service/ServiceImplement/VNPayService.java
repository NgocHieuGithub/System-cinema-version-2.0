package system.system_cinema.Service.ServiceImplement;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import system.system_cinema.Config.VNPAYConfig;
import system.system_cinema.DTO.Request.DetailsFvB;
import system.system_cinema.DTO.Request.LockSeatsRequest;
import system.system_cinema.Enum.StatusOrder;
import system.system_cinema.Enum.StatusSeat;
import system.system_cinema.Model.FoodOrder;
import system.system_cinema.Model.SeatBooking;
import system.system_cinema.Model.Ticket;
import system.system_cinema.Repository.*;
import system.system_cinema.Service.IVNPayService;
import system.system_cinema.Utils.VNPayUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    RedissonClient redissonClient;

    public boolean LockSeat(List<Integer> values, int v2) {
        List<RLock> locks = new ArrayList<>();
        try {
            for (Integer value : values) {
                String key = "lock:" + value + "-" + v2;
                RLock lock = redissonClient.getLock(key);
                if (lock.tryLock(5, 10, TimeUnit.SECONDS)) {
                    locks.add(lock);
                } else {
                    for (RLock acquiredLock : locks) {
                        acquiredLock.unlock();
                    }
                    return false;
                }
            }
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Cache operation interrupted", e);
        }
    }

    @Override
    @Transactional
    public String HandleOrder(HttpServletRequest request, @RequestBody LockSeatsRequest lockSeatsRequest) {
        if (LockSeat(lockSeatsRequest.getSeatIds(), lockSeatsRequest.getShowtimeId())) {
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
        } else {
            throw new RuntimeException("Invalid seat booking request");
        }
    }

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
