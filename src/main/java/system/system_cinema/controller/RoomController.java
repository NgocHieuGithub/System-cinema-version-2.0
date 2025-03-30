package system.system_cinema.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.dto.request.RoomCreateRequest;
import system.system_cinema.dto.request.ShowtimeRequest;
import system.system_cinema.dto.ApiResponse;
import system.system_cinema.dto.response.RoomResponse;
import system.system_cinema.service.IRoomService;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomController {
    IRoomService roomService;

    @GetMapping("/get/{id}")
    public ApiResponse<RoomResponse> getRoomById(@PathVariable @NotNull int id) {
        return ApiResponse.<RoomResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(roomService.getRoomById(id))
                .build();
    }

    @GetMapping("/get-all")
    public ApiResponse<List<RoomResponse>> getAllRoom() {
        return ApiResponse.<List<RoomResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(roomService.getAllRoom())
                .build();
    }

    @PutMapping("/change")
    public ApiResponse<RoomResponse> changeRoomStatus(@RequestParam @NotNull int id) {
        return ApiResponse.<RoomResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Status changed successfully")
                .data(roomService.changeCinemaHallStatus(id))
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<?> createRoom(@Valid @RequestBody RoomCreateRequest request) {
        roomService.createRoom(request);
        return ApiResponse.<RoomResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Showtime added successfully")
                .build();
    }

    @PostMapping("/add-showtime/{cinemaHallId}")
    public ApiResponse<RoomResponse> addShowtime(@PathVariable @NotNull int cinemaHallId, @Valid @RequestBody ShowtimeRequest showtimeRequest) {
        return ApiResponse.<RoomResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Showtime added successfully")
                .data(roomService.addShowtime(cinemaHallId, showtimeRequest))
                .build();
    }

    @GetMapping("/check-available-room")
    public ApiResponse<List<RoomResponse>> checkAvailableRoom(@RequestParam @NotNull LocalDateTime time) {
        return ApiResponse.<List<RoomResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Showtime added successfully")
                .data(roomService.checkAvailability(time))
                .build();
    }
}
