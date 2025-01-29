package system.system_cinema.Service;

import system.system_cinema.DTO.Request.SeatBookingRequest;
import system.system_cinema.DTO.Response.SeatBookingResponse;

import java.util.List;

public interface ISeatBookingService {
    boolean lockSeats(List<Integer> seatIds, int showtimeId, int userId);
}
