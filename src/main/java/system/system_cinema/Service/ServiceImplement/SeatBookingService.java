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
    SeatBookingRepository seatBookingRepository;
    @Override
    @Transactional
    public boolean lockSeats(List<Integer> seatIds, int showtimeId, int userId) {
        List<SeatBooking> existingSeats = seatBookingRepository.findBySeatIdInAndShowTimeId(seatIds, showtimeId);

        for (SeatBooking seat : existingSeats) {
            if ("sold".equals(seat.getStatus()) || "held".equals(seat.getStatus())) {
                return false;
            }
        }

        for (Integer seatId : seatIds) {
            SeatBooking seatBooking = SeatBooking.builder().build();
//            seatBooking.setStatus("held");
            seatBookingRepository.save(seatBooking);
        }

        return true;
    }
}
