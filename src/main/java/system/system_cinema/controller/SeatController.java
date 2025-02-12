package system.system_cinema.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.dto.ApiResponse;
import system.system_cinema.dto.request.SeatRequest;
import system.system_cinema.dto.response.SeatResponse;
import system.system_cinema.service.ISeatService;
import java.util.List;

@RestController
@RequestMapping("/seats")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SeatController {

    ISeatService seatService;

    @GetMapping("/get-by-show-time/{showtimeId}")
    public ApiResponse<List<SeatResponse>> getSeatsByRoom(@PathVariable @NotNull int showtimeId) {
        return ApiResponse.<List<SeatResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(seatService.getSeatsByCinemaHall(showtimeId))
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/create")
    public ApiResponse<SeatResponse> createSeat(@Valid @RequestBody SeatRequest seatRequest) {
        return ApiResponse.<SeatResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Seat created successfully")
                .data(seatService.createSeat(seatRequest))
                .build();
    }
}
