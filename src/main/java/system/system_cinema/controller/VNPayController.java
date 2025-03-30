package system.system_cinema.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.dto.ApiResponse;
import system.system_cinema.dto.request.LockSeatsRequest;
import system.system_cinema.constant.StatusOrder;
import system.system_cinema.domain.Ticket;
import system.system_cinema.repository.BookingRepository;
import system.system_cinema.service.IVNPayService;
import java.io.IOException;


@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VNPayController {

    IVNPayService vnPayService;
    BookingRepository bookingRepository;

    @PostMapping("/vn-pay")
    public ApiResponse<?> pay(HttpServletRequest request, @Valid @RequestBody LockSeatsRequest lockSeatsRequest) {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(vnPayService.handleOrder(request, lockSeatsRequest))
                .build();
    }

    @GetMapping("/vn-pay-callback")
    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer idTicket = Integer.valueOf(request.getParameter("ticket"));
        Ticket ticket = bookingRepository.findById(idTicket).orElseThrow(() -> new RuntimeException("Ticket not found"));
        String url_direct;
        if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
            ticket.setStatus(StatusOrder.SUCCESS);
            url_direct = "http://localhost:3000/payment-success";
        } else {
            ticket.setStatus(StatusOrder.FAILED);
            url_direct = "http://localhost:3000/payment-failure";
        }
        bookingRepository.save(ticket);
        response.sendRedirect(url_direct);
    }

//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    @GetMapping("/status")
//    public ResponseEntity<?> getBookingStatus(@RequestParam String requestId) {
//        String status = redisTemplate.opsForValue().get("booking:" + requestId);
//        return ResponseEntity.ok(Map.of("status", Objects.requireNonNullElse(status, "processing")));
//    }

}
