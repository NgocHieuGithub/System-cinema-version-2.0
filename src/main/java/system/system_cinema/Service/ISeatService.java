package system.system_cinema.Service;

import system.system_cinema.DTO.Request.SeatRequest;
import system.system_cinema.DTO.Response.SeatResponse;

import java.util.List;

public interface ISeatService {

    List<SeatResponse> getSeatsByCinemaHall(int showTimeId);

    SeatResponse createSeat(SeatRequest request);

    SeatResponse updateSeat(int id, SeatRequest request);

    void deleteSeat(int id);
}
