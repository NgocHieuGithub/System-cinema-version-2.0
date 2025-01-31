package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.Request.ShowtimeRequest;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Response.RoomResponse;
import system.system_cinema.Service.IRoomService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomController {
    IRoomService roomService;

    @GetMapping("/get/{id}")
    public ApiResponse<RoomResponse> getCinemaHallById(@PathVariable int id) {
        try {
            return ApiResponse.<RoomResponse>builder()
                    .message("Successful")
                    .data(roomService.getCinemaHallById(id))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<RoomResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/get-all")
    public ApiResponse<List<RoomResponse>> getAllRoom() {
        try {
            return ApiResponse.<List<RoomResponse>>builder()
                    .message("Successful")
                    .data(roomService.getAllCinemaHalls())
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<RoomResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PutMapping("/change")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<RoomResponse> ChangeRoomStatus(@RequestParam int id) {
        try {
            return ApiResponse.<RoomResponse>builder()
                    .message("Status changed successfully")
                    .data(roomService.changeCinemaHallStatus(id))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<RoomResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/add-showtime/{cinemaHallId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<RoomResponse> addShowtime(@PathVariable int cinemaHallId, @RequestBody ShowtimeRequest showtimeRequest) {
        try {
            return ApiResponse.<RoomResponse>builder()
                    .message("Showtime added successfully")
                    .data(roomService.addShowtime(cinemaHallId, showtimeRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<RoomResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @GetMapping("/check-available-room")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<List<RoomResponse>> checkAvailableRoom(@RequestParam LocalDateTime time ) {
        try {
            return ApiResponse.<List<RoomResponse>>builder()
                    .message("Showtime added successfully")
                    .data(roomService.checkAvailability(time))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<RoomResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
