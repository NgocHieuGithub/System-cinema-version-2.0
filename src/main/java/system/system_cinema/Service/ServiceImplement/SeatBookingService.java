package system.system_cinema.Service.ServiceImplement;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import system.system_cinema.Model.SeatBooking;
import system.system_cinema.Repository.SeatBookingRepository;
import system.system_cinema.Service.ISeatBookingService;

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
