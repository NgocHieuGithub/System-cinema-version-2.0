package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Response.*;
import system.system_cinema.Service.ITicketService;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin("http://localhost:3000")
public class TicketController {

    final ITicketService ticketService;


    @GetMapping("/get-by-user/{userId}")
    public ApiResponse<List<TicketResponse>> getTicketsByUser(@PathVariable String userId) {
        try {
            return ApiResponse.<List<TicketResponse>>builder()
                    .message("Successful")
                    .data(ticketService.getTicketsByUser(userId))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<TicketResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @GetMapping("/get-statistic-total-price")
    public ApiResponse<List<StatisticPriceMonthResponse>> MonthlyStatistics() {
        try {
            return ApiResponse.<List<StatisticPriceMonthResponse>>builder()
                    .message("Successful")
                    .data(ticketService.statisticPriceMonth())
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<StatisticPriceMonthResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @GetMapping("/get-statistic-total-ticket-sold-by-user")
    public ApiResponse<List<StatisticUserTicket>> MonthlyStatisticsByUser() {
        try {
            return ApiResponse.<List<StatisticUserTicket>>builder()
                    .message("Successful")
                    .data(ticketService.statisticUserTicket())
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<StatisticUserTicket>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @GetMapping("/get-statistic-revenue-movie")
    public ApiResponse<List<StatisticMovieRevenue>> MonthlyStatisticRevenueMovie() {
        try {
            return ApiResponse.<List<StatisticMovieRevenue>>builder()
                    .message("Successful")
                    .data(ticketService.statisticMovieRevenue())
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<StatisticMovieRevenue>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @GetMapping("/get-status-ticket")
    public ApiResponse<List<StatusTicket>> getStatusTicket() {
        try {
            return ApiResponse.<List<StatusTicket>>builder()
                    .message("Successful")
                    .data(ticketService.getStatusTickets())
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<StatusTicket>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
