package system.system_cinema.Mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Request.ShowtimeRequest;
import system.system_cinema.DTO.Response.ShowTimeAndRoomResponse;
import system.system_cinema.DTO.Response.ShowtimeResponse;
import system.system_cinema.Model.Movie;
import system.system_cinema.Model.Showtime;

@Component
@Mapper(componentModel = "spring")
public interface ShowtimeMapper {
    ShowtimeResponse toShowtimeResponse(Showtime showtime) ;
    Showtime toShowtime(ShowtimeRequest showtimeRequest, Movie movie);
    ShowTimeAndRoomResponse convertShowTimeClean(Showtime showtime);
}
