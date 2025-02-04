package system.system_cinema.Service.ServiceImplement;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import system.system_cinema.Config.VNPAYConfig;
import system.system_cinema.DTO.Request.DetailsFvB;
import system.system_cinema.DTO.Request.LockSeatsRequest;
import system.system_cinema.Enum.StatusOrder;
import system.system_cinema.Model.FoodOrder;
import system.system_cinema.Model.SeatBooking;
import system.system_cinema.Model.Ticket;
import system.system_cinema.Repository.*;
import system.system_cinema.Service.IVNPayService;
import system.system_cinema.Utils.VNPayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)

public class VNPayService implements IVNPayService {
    SeatBookingRepository seatBookingRepository;
    VNPAYConfig vnPayConfig;
    SeatRepository seatRepository;
    BookingRepository bookingRepository;
    ShowTimeRepository showTimeRepository;
    UserRepository userRepository;
    SnackRepository snackRepository;
    ComboRepository comboRepository;

    @Override
    @Transactional
    public String CreateVNPayPayment(HttpServletRequest request, @RequestBody LockSeatsRequest lockSeatsRequest) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        HandleLock(lockSeatsRequest.getSeatIds(), lockSeatsRequest.getShowtimeId());

        List<SeatBooking> seatBookings = new ArrayList<>();
        for (Integer seatId : lockSeatsRequest.getSeatIds()) {
            SeatBooking seatBooking = SeatBooking
                    .builder()
                    .seat(seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found")))
                    .status(StatusOrder.ORDER)
                    .build();
            seatBookings.add(seatBooking);
        }
        List<FoodOrder> foodBeverageOrders = new ArrayList<>();
        if (lockSeatsRequest.getSnack() != null){
            for (DetailsFvB d : lockSeatsRequest.getSnack()) {
                FoodOrder f = FoodOrder.builder()
                        .quantity(d.getQuantity())
                        .snack(snackRepository.findById(d.getId()).orElseThrow(() -> new RuntimeException("Snack not found")))
                        .build();
                foodBeverageOrders.add(f);
            }
        }
        if (lockSeatsRequest.getCombo() != null){
            for (DetailsFvB d : lockSeatsRequest.getCombo()) {
                FoodOrder f = FoodOrder.builder()
                        .quantity(d.getQuantity())
                        .combo(comboRepository.findById(d.getId()).orElseThrow(()->new RuntimeException("Combo not found")))
                        .build();
                foodBeverageOrders.add(f);
            }
        }
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

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(idTicket);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        return vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
    }

    @Transactional
    public void HandleLock (List<Integer> v1, int v2){
        List<SeatBooking> existingSeats = seatBookingRepository.findBySeatIdInAndShowTimeId(v1, v2);
        for (SeatBooking seat : existingSeats) {
            if (seat.getStatus().equals(StatusOrder.ORDER)) {
                throw new RuntimeException("Sold Seat is already held");
            }
        }
    }
}
