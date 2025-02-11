package system.system_cinema.service.serviceImplement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import system.system_cinema.dto.request.DetailShowTime;
import system.system_cinema.dto.request.ShowTimeRequestCreate;
import system.system_cinema.dto.response.ShowTimeAndRoomResponse;
import system.system_cinema.mapper.ShowtimeMapper;
import system.system_cinema.domain.Movie;
import system.system_cinema.domain.Showtime;
import system.system_cinema.domain.Ticket;
import system.system_cinema.repository.BookingRepository;
import system.system_cinema.repository.MovieRepository;
import system.system_cinema.repository.RoomRepository;
import system.system_cinema.repository.ShowTimeRepository;
import system.system_cinema.service.IShowTimeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShowTimeService implements IShowTimeService {

    RoomRepository roomRepository;
    MovieRepository movieRepository;
    ShowTimeRepository showtimeRepository;
    ShowtimeMapper showtimeMapper;
    BookingRepository bookingRepository;
    @Override
    public void createShowTime(ShowTimeRequestCreate requestCreate) {
        if (requestCreate.getMovieId() != 0
                && requestCreate.getTimeSheet() != null) {
            Movie movie = movieRepository.findById(requestCreate.getMovieId())
                    .orElseThrow(() -> new RuntimeException("Movie not found"));
            List<Showtime> showTimes = new ArrayList<>();
            LocalDateTime today = LocalDateTime.now();
            for (DetailShowTime d : requestCreate.getTimeSheet()){
                showTimes.add(
                        Showtime
                                .builder()
                                .room(roomRepository.findById(d.getRoomId())
                                        .orElseThrow(() -> new RuntimeException("Room not found")))
                                .movie(movie)
                                .dateCreate(today)
                                .startTime(d.getTimeStart())
                                .endTime(d.getTimeStart().plusMinutes(movie.getDuration()).minusMinutes(1))
                                .build()
                );
            }
            showtimeRepository.saveAll(showTimes);
        } else {
            throw new RuntimeException("Data from request must not null");
        }
    }

    @Override
    public void updateShowTime(int showTimeId, int roomId) {
        Showtime showtime = showtimeRepository.findById(showTimeId).orElseThrow(() -> new RuntimeException("ShowTime not found"));
        showtime.setRoom(roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found")));
        showtimeRepository.save(showtime);
    }

    @Override
    public void deleteShowTime(int showTimeId) {
        Showtime showtime = showtimeRepository.findById(showTimeId).orElseThrow(() -> new RuntimeException("Show time not found"));
        List<Ticket> ticket = bookingRepository.findByShowtime(showtime);
        if (ticket.isEmpty()) {
           showtimeRepository.delete(showtime);
        } else {
            throw new RuntimeException("Showtime has been booked, can not delete");
        }
    }

    @Override
    public Map<String, List<String>> getListShowTime(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        List<Showtime> showTimes = showtimeRepository.findByStartTimeBetweenOrderByMovie(startOfDay, endOfDay);
        return showTimes.stream()
                .collect(Collectors.groupingBy(
                        showtime -> showtime.getMovie().getTitle(),
                        Collectors.mapping(
                                showtime -> String.format("%02d:%02d - %s",
                                        showtime.getStartTime().getHour(),
                                        showtime.getStartTime().getMinute(),
                                        showtime.getRoom().getName()
                                ),
                                Collectors.toList()
                        )
                ));
    }

    @Override
    public Map<String, Object> getAllShowTimes(int page) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by("dateCreate").descending());
        Page<Showtime> pageShowtime = showtimeRepository.findAll(pageable);
        List<ShowTimeAndRoomResponse> showTimes = pageShowtime.getContent().stream().map(showtimeMapper::convertShowTimeClean).toList();
        int totalPages = pageShowtime.getTotalPages();

        Map<String, Object> response = new HashMap<>();
        response.put("showtime", showTimes);
        response.put("totalPages", totalPages);
        response.put("currentPage", pageShowtime.getNumber());
        return response;
    }

}
