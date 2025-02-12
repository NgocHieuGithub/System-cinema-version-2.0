package system.system_cinema.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.system_cinema.dto.request.MovieRequest;
import system.system_cinema.dto.response.MovieResponse;
import system.system_cinema.domain.Movie;

@Component
@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieResponse toResponse(Movie movie);
    Movie toMovie(MovieRequest movieRequest);
}
