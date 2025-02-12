package system.system_cinema.service;

import system.system_cinema.dto.request.RoomCreateRequest;
import system.system_cinema.dto.request.ShowtimeRequest;
import system.system_cinema.dto.response.RoomResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IRoomService {
    RoomResponse getRoomById(int id);
    List<RoomResponse> getAllRoom();
    RoomResponse changeCinemaHallStatus(int id);
    RoomResponse addShowtime(int cinemaHallId, ShowtimeRequest showtimeRequest);
    List<RoomResponse> checkAvailability(LocalDateTime time);
    void createRoom(RoomCreateRequest roomCreateRequest);
}
