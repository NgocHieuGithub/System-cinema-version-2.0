package system.system_cinema.service.serviceImplement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import system.system_cinema.dto.request.RoomCreateRequest;
import system.system_cinema.dto.request.ShowtimeRequest;
import system.system_cinema.dto.response.RoomResponse;
import system.system_cinema.constant.Status;
import system.system_cinema.constant.TypeSeat;
import system.system_cinema.mapper.RoomMapper;
import system.system_cinema.domain.Movie;
import system.system_cinema.domain.Room;
import system.system_cinema.domain.Seat;
import system.system_cinema.domain.Showtime;
import system.system_cinema.repository.MovieRepository;
import system.system_cinema.repository.RoomRepository;
import system.system_cinema.repository.SeatRepository;
import system.system_cinema.repository.ShowTimeRepository;
import system.system_cinema.service.IRoomService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomService implements IRoomService {
    ShowTimeRepository showtimeRepository;
    MovieRepository movieRepository;
    RoomMapper roomMapper;
    RoomRepository roomRepository;
    SeatRepository seatRepository;

    @Override
    public RoomResponse getRoomById(int id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return roomMapper.toRoom(room);
    }

    @Override
    public List<RoomResponse> getAllRoom() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toRoom)
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponse changeCinemaHallStatus(int id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        room.setStatus(room.getStatus().equals(Status.ACTIVE) ? Status.INACTIVE : Status.ACTIVE);
        Room updatedRoom = roomRepository.save(room);
        return roomMapper.toRoom(updatedRoom);
    }

    @Override
    public RoomResponse addShowtime(int roomId, ShowtimeRequest showtimeRequest) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Movie movie = movieRepository.findById(showtimeRequest.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Showtime showtime = Showtime.builder()
                .room(room)
                .movie(movie)
                .startTime(showtimeRequest.getStartTime())
                .endTime(showtimeRequest.getEndTime())
                .build();

        showtimeRepository.save(showtime);

        return roomMapper.toRoom(room);
    }

    @Override
    public List<RoomResponse> checkAvailability(LocalDateTime time) {
        List<Showtime> ongoingShowtimes = showtimeRepository.findByEndTimeAfter(time);

        // Lấy danh sách các phòng đang có suất chiếu tại thời điểm được cung cấp
        List<Room> occupiedRooms = ongoingShowtimes.stream()
                .map(Showtime::getRoom)
                .distinct()
                .toList();

        // Tìm các phòng không có suất chiếu nào đang diễn ra hoặc sắp diễn ra sau thời điểm đó
        return roomRepository.findAll().stream()
                .filter(room -> !occupiedRooms.contains(room))
                .toList().stream().map(roomMapper::toRoom).toList();
    }

    @Override
    public void createRoom(RoomCreateRequest request) {
        if (roomRepository.existsByName(request.getName())){
            throw new RuntimeException("Room name already exists");
        }
        Room room = new Room();
        room.setName(request.getName());
        room.setQuantity(request.getVipSeats() + request.getCoupleSeats() + request.getNormalSeats());
        room.setStatus(Status.ACTIVE);
        Room savedRoom = roomRepository.save(room);

        List<Seat> seats = new ArrayList<>();
        if (request.getVipSeats() != 0){
            for (int i = 1; i <= request.getVipSeats(); i++) {
                Seat seat = new Seat();
                seat.setRoom(savedRoom);
                seat.setType(TypeSeat.Vip);
                seat.setSeatNumber("V-" + i);
                seats.add(seat);
            }
        }

        if (request.getNormalSeats() != 0){
            for (int i = 1; i <= request.getNormalSeats(); i++) {
                Seat seat = new Seat();
                seat.setRoom(savedRoom);
                seat.setType(TypeSeat.Normal);
                seat.setSeatNumber("N-" + i);
                seats.add(seat);
            }

        }

        if (request.getCoupleSeats() != 0){
            for (int i = 1; i <= request.getCoupleSeats(); i++) {
                Seat seat = new Seat();
                seat.setRoom(savedRoom);
                seat.setType(TypeSeat.Couple);
                seat.setSeatNumber("C-" + i);
                seats.add(seat);
            }

        }
        seatRepository.saveAll(seats);
    }
}
