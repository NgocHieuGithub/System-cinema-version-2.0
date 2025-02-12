package system.system_cinema.service;

import org.springframework.web.multipart.MultipartFile;
import system.system_cinema.dto.request.MovieRequest;
import system.system_cinema.dto.response.MovieResponse;

import java.io.IOException;
import java.util.List;

public interface IMovieService {
    List<MovieResponse> getAllMovies();
    MovieResponse getMovieById(int id);
    void createMovie(MovieRequest movieRequest, MultipartFile movieImage) throws IOException;
    void updateMovie(int id, MovieRequest movieRequest, MultipartFile movieImage) throws IOException;
    void deleteMovie(int id);
    List<MovieResponse> searchMovie(String keyWords);
}
