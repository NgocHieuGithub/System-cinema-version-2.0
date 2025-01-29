package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.LockSeatsRequest;
import system.system_cinema.Service.ISeatBookingService;

@RestController
@RequestMapping("/seat-bookings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin("http://localhost:3000")
public class SeatBookingController {

    ISeatBookingService seatBookingService;

    @PostMapping("/lock-seats")
    public ApiResponse<?> lockSeats(@RequestBody LockSeatsRequest request) {
        boolean allLocked = seatBookingService.lockSeats(request.getSeatIds(), request.getShowtimeId(), request.getUserId());
        try {
            if (allLocked) {
                return ApiResponse
                        .builder()
                        .message("Seats locked successfully")
                        .build();
            } else {
                return ApiResponse
                        .builder()
                        .message("Some seats are already held or sold")
                        .build();
            }
        } catch (Exception e) {
            return ApiResponse
                    .builder()
                    .message(e.getMessage())
                    .build();
        }
    }

}
