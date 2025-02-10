package system.system_cinema.Service;

import java.util.List;

public interface ISeatBookingService {
    boolean lockSeats(List<Integer> seatIds, int showtimeId, int userId);
}
