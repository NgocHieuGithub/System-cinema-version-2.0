package system.system_cinema.Service;

import system.system_cinema.DTO.Request.RoomCreateRequest;
import system.system_cinema.DTO.Request.ShowtimeRequest;
import system.system_cinema.DTO.Response.RoomResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IRoomService {
    RoomResponse getRoomById(int id);
    List<RoomResponse> getAllRoom();
    RoomResponse changeCinemaHallStatus(int id);
    RoomResponse addShowtime(int cinemaHallId, ShowtimeRequest showtimeRequest);
    List<RoomResponse> checkAvailability(LocalDateTime time);
    void CreateRoom(RoomCreateRequest roomCreateRequest);
}
