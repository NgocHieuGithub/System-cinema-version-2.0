package system.system_cinema.Service.ServiceImplement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.ShowtimeRequest;
import system.system_cinema.DTO.Response.RoomResponse;
import system.system_cinema.Enum.Status;
import system.system_cinema.Mapper.RoomMapper;
import system.system_cinema.Model.Movie;
import system.system_cinema.Model.Room;
import system.system_cinema.Model.Showtime;
import system.system_cinema.Repository.MovieRepository;
import system.system_cinema.Repository.RoomRepository;
import system.system_cinema.Repository.ShowTimeRepository;
import system.system_cinema.Service.IRoomService;

import java.time.LocalDateTime;
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

    @Override
    public RoomResponse getCinemaHallById(int id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return roomMapper.toCinemaHallResponse(room);
    }

    @Override
    public List<RoomResponse> getAllCinemaHalls() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toCinemaHallResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponse changeCinemaHallStatus(int id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        room.setStatus(room.getStatus().equals(Status.ACTIVE) ? Status.INACTIVE : Status.ACTIVE);
        Room updatedRoom = roomRepository.save(room);
        return roomMapper.toCinemaHallResponse(updatedRoom);
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

        return roomMapper.toCinemaHallResponse(room);
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
                .toList().stream().map(roomMapper::toCinemaHallResponse).toList();
    }
}
