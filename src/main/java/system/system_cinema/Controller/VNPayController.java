package system.system_cinema.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.LockSeatsRequest;
import system.system_cinema.Enum.StatusOrder;
import system.system_cinema.Model.Ticket;
import system.system_cinema.Repository.BookingRepository;
import system.system_cinema.Service.IVNPayService;
import java.io.IOException;


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
                    .data(service.HandleOrder(request, lockSeatsRequest))
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
        Integer idTicket = Integer.valueOf(request.getParameter("ticket"));
        Ticket ticket = bookingRepository.findById(idTicket).orElseThrow(() -> new RuntimeException("Ticket not found"));
        String url_direct;
        if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
            ticket.setStatus(StatusOrder.ORDER);
            url_direct = "http://localhost:3000/payment-success";
        } else {
            ticket.setStatus(StatusOrder.INORDER);
            url_direct = "http://localhost:3000/payment-failure";
        }
        bookingRepository.save(ticket);
        response.sendRedirect(url_direct);
    }
}
