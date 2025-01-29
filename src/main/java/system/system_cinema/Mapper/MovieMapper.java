package system.system_cinema.Mapper;

import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Request.MovieRequest;
import system.system_cinema.DTO.Response.MovieResponse;
import system.system_cinema.Model.Comment;
import system.system_cinema.Model.Movie;

@Component
public class MovieMapper {

    public MovieResponse toMovieResponse(Movie movie) {
        double averageRating = movie.getComments().stream()
                .mapToInt(Comment::getRate)
                .average()
                .orElse(0.0);
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .description(movie.getDescription())
                .duration(movie.getDuration())
                .actor(movie.getActors())
                .director(movie.getDirector())
                .releaseDate(movie.getReleaseDate())
                .image(movie.getImage())
                .isActive(movie.isActive())
                .averageRating(averageRating)
                .build();
    }

    public Movie toMovie(MovieRequest movieRequest) {
        return Movie.builder()
                .title(movieRequest.getTitle())
                .genre(movieRequest.getGenre())
                .description(movieRequest.getDescription())
                .duration(movieRequest.getDuration())
                .actors(movieRequest.getActor())
                .director(movieRequest.getDirector())
                .releaseDate(movieRequest.getReleaseDate())
                .build();
    }
}
