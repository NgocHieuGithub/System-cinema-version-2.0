package system.system_cinema.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get/{id}")
    public ApiResponse<RoomResponse> getRoomById(@PathVariable @NotNull int id) {
        return ApiResponse.<RoomResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(roomService.getCinemaHallById(id))
                .build();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-all")
    public ApiResponse<List<RoomResponse>> getAllRoom() {
        return ApiResponse.<List<RoomResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(roomService.getAllCinemaHalls())
                .build();
    }

    @PutMapping("/change")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<RoomResponse> ChangeRoomStatus(@RequestParam @NotNull int id) {
        return ApiResponse.<RoomResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Status changed successfully")
                .data(roomService.changeCinemaHallStatus(id))
                .build();
    }

    @PostMapping("/add-showtime/{cinemaHallId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<RoomResponse> addShowtime(@PathVariable @NotNull int cinemaHallId, @Valid @RequestBody ShowtimeRequest showtimeRequest) {
        return ApiResponse.<RoomResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Showtime added successfully")
                .data(roomService.addShowtime(cinemaHallId, showtimeRequest))
                .build();
    }

    @GetMapping("/check-available-room")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<List<RoomResponse>> checkAvailableRoom(@RequestParam @NotNull LocalDateTime time) {
        return ApiResponse.<List<RoomResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Showtime added successfully")
                .data(roomService.checkAvailability(time))
                .build();
    }
}
