package system.system_cinema.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.LockSeatsRequest;
import system.system_cinema.Model.Ticket;
import system.system_cinema.Repository.BookingRepository;
import system.system_cinema.Service.IVNPayService;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VNPayController {
    IVNPayService service;
    private final BookingRepository bookingRepository;

    @PostMapping("/vn-pay")
    public ApiResponse<?> pay(HttpServletRequest request,@RequestBody LockSeatsRequest lockSeatsRequest) {
        System.out.println(lockSeatsRequest.getShowtimeId() + " " + lockSeatsRequest.getUserId());
        try {
            return ApiResponse
                    .builder()
                    .data(service.CreateVNPayPayment(request, lockSeatsRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse
                    .builder()
                    .message(e.getMessage())
                    .build();
        }
    }
    @GetMapping("/vn-pay-callback")
    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idTicket = request.getParameter("ticketID");
        String status = request.getParameter("vnp_ResponseCode");
        Ticket ticket = bookingRepository.findById(idTicket).orElseThrow(() -> new RuntimeException("Ticket not found"));
        if (status.equals("00")) {
            ticket.setDateBooking(LocalDateTime.now());
            ticket.setPaid(true);
            bookingRepository.save(ticket);
            response.sendRedirect("http://localhost:3000/payment-success");
        } else {
            bookingRepository.deleteById(idTicket);
            response.sendRedirect("http://localhost:3000/payment-failure");
        }
    }
}
