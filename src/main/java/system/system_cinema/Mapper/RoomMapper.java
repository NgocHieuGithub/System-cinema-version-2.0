package system.system_cinema.Mapper;

import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Response.RoomResponse;
import system.system_cinema.DTO.Response.ShowtimeResponse;
import system.system_cinema.Model.Room;
import system.system_cinema.Model.Showtime;
import java.util.stream.Collectors;

@Component
public class RoomMapper {

    public RoomResponse toCinemaHallResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .status(room.getStatus())
                .showtimes(room.getShowtimes().stream()
                        .map(this::toShowtimeResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    private ShowtimeResponse toShowtimeResponse(Showtime showtime) {
        return ShowtimeResponse.builder()
                .id(showtime.getId())
                .movieTitle(showtime.getMovie().getTitle())
                .startTime(showtime.getStartTime())
                .endTime(showtime.getEndTime())
                .build();
    }
}
