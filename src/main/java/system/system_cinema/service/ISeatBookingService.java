package system.system_cinema.service;

import java.util.List;

public interface ISeatBookingService {
    boolean lockSeats(List<Integer> seatIds, int showtimeId, int userId);
}
