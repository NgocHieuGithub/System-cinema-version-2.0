package system.system_cinema.service.serviceImplement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import system.system_cinema.service.ISeatBookingService;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SeatBookingService implements ISeatBookingService {

    @Override
    public boolean lockSeats(List<Integer> seatIds, int showtimeId, int userId) {
        return false;
    }
}
