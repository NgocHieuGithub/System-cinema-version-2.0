package system.system_cinema.Service.ServiceImplement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.SeatRequest;
import system.system_cinema.DTO.Response.SeatResponse;
import system.system_cinema.Enum.TypeSeat;
import system.system_cinema.Mapper.SeatMapper;
import system.system_cinema.Model.Room;
import system.system_cinema.Model.Seat;
import system.system_cinema.Repository.RoomRepository;
import system.system_cinema.Repository.SeatRepository;
import system.system_cinema.Service.ISeatService;

import java.util.List;

@Service
@RequiredArgsConstructor
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
        Room room = roomRepository.findById(request.getCinemaHallId())
                .orElseThrow(() -> new RuntimeException("Cinema Hall not found"));

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

        Room room = roomRepository.findById(request.getCinemaHallId())
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
