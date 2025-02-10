package system.system_cinema.Mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Request.MovieRequest;
import system.system_cinema.DTO.Response.MovieResponse;
import system.system_cinema.Model.Comment;
import system.system_cinema.Model.Movie;

@Component
@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieResponse toResponse(Movie movie);
    Movie toMovie(MovieRequest movieRequest);
}
