package system.system_cinema.service.serviceImplement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import system.system_cinema.dto.request.SeatRequest;
import system.system_cinema.dto.response.SeatResponse;
import system.system_cinema.constant.TypeSeat;
import system.system_cinema.mapper.SeatMapper;
import system.system_cinema.domain.Room;
import system.system_cinema.domain.Seat;
import system.system_cinema.repository.RoomRepository;
import system.system_cinema.repository.SeatRepository;
import system.system_cinema.service.ISeatService;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SeatService implements ISeatService {

    SeatRepository seatRepository;
    RoomRepository roomRepository;
    SeatMapper seatMapper;

    @Override
    public List<SeatResponse> getSeatsByCinemaHall(int showTimeId) {
        return seatRepository.findSeatsWithTypeAndStatusByShowTime(showTimeId);
    }

    @Override
    public SeatResponse createSeat(SeatRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Seat seat = seatMapper.toSeat(request);
        seat.setRoom(room);
        seat.setType(TypeSeat.Normal);

        Seat savedSeat = seatRepository.save(seat);
        return seatMapper.toSeatResponse(savedSeat);
    }

    @Override
    public SeatResponse updateSeat(int id, SeatRequest request) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Cinema Hall not found"));

        seat.setSeatNumber(request.getSeatNumber());
        seat.setRoom(room);
        seat.setType(TypeSeat.Normal);

        Seat updatedSeat = seatRepository.save(seat);
        return seatMapper.toSeatResponse(updatedSeat);
    }

    @Override
    public void deleteSeat(int id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        seatRepository.delete(seat);
    }
}
