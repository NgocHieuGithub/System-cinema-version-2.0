package system.system_cinema.controller;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.dto.ApiResponse;
import system.system_cinema.dto.response.*;
import system.system_cinema.service.ITicketService;
import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketController {

    ITicketService ticketService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/get-by-user/{userId}")
    public ApiResponse<List<TicketResponse>> getTicketsByUser(@PathVariable @NotNull int userId) {
        return ApiResponse.<List<TicketResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(ticketService.getTicketsByUser(userId))
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-statistic-total-price")
    public ApiResponse<List<StatisticPriceMonthResponse>> monthlyStatistics() {
        return ApiResponse.<List<StatisticPriceMonthResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(ticketService.statisticPriceMonth())
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-statistic-total-ticket-sold-by-user")
    public ApiResponse<List<StatisticUserTicket>> monthlyStatisticsByUser() {
        return ApiResponse.<List<StatisticUserTicket>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(ticketService.statisticUserTicket())
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-statistic-revenue-movie")
    public ApiResponse<List<StatisticMovieRevenue>> monthlyStatisticRevenueMovie() {
        return ApiResponse.<List<StatisticMovieRevenue>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(ticketService.statisticMovieRevenue())
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-status-ticket")
    public ApiResponse<List<StatusTicket>> getStatusTicket() {
        return ApiResponse.<List<StatusTicket>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(ticketService.getStatusTickets())
                .build();
    }
}
