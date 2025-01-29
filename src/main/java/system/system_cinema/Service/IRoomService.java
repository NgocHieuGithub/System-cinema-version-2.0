package system.system_cinema.Service;

import system.system_cinema.DTO.Request.ShowtimeRequest;
import system.system_cinema.DTO.Response.RoomResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IRoomService {
    RoomResponse getCinemaHallById(int id);
    List<RoomResponse> getAllCinemaHalls();
    RoomResponse changeCinemaHallStatus(int id, boolean isActive);
    RoomResponse addShowtime(int cinemaHallId, ShowtimeRequest showtimeRequest);
    List<RoomResponse> checkAvailability(LocalDateTime time);
}
