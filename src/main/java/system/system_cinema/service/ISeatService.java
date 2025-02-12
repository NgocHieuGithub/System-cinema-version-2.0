package system.system_cinema.service;

import system.system_cinema.dto.request.SeatRequest;
import system.system_cinema.dto.response.SeatResponse;

import java.util.List;

public interface ISeatService {

    List<SeatResponse> getSeatsByCinemaHall(int showTimeId);

    SeatResponse createSeat(SeatRequest request);

    SeatResponse updateSeat(int id, SeatRequest request);

    void deleteSeat(int id);
}
