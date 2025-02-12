package system.system_cinema.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import system.system_cinema.dto.request.ShowtimeRequest;
import system.system_cinema.dto.response.ShowTimeAndRoomResponse;
import system.system_cinema.dto.response.ShowtimeResponse;
import system.system_cinema.domain.Movie;
import system.system_cinema.domain.Showtime;

@Component
@Mapper(componentModel = "spring")
public interface ShowtimeMapper {
    ShowtimeResponse toShowtimeResponse(Showtime showtime) ;
    Showtime toShowtime(ShowtimeRequest showtimeRequest, Movie movie);
    @Mapping(source = "movie.title", target = "movie")
    ShowTimeAndRoomResponse convertShowTimeClean(Showtime showtime);
}
