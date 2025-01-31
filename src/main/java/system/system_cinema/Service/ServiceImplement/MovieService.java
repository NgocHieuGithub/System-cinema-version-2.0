package system.system_cinema.Service.ServiceImplement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import system.system_cinema.DTO.Request.MovieRequest;
import system.system_cinema.DTO.Response.MovieResponse;
import system.system_cinema.Enum.Status;
import system.system_cinema.Mapper.MovieMapper;
import system.system_cinema.Model.Movie;
import system.system_cinema.Repository.MovieRepository;
import system.system_cinema.Service.IMovieService;
import system.system_cinema.Model.Comment;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieService implements IMovieService {

    MovieRepository movieRepository;
    MovieMapper movieMapper;
    FileUpload fileUploadImpl;

    @Override
    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(movieMapper::toMovieResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MovieResponse getMovieById(int id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        return movieMapper.toMovieResponse(movie);
    }

    @Override
    public void createMovie(MovieRequest movieRequest, MultipartFile movieImage) throws IOException {
        Movie movie = movieMapper.toMovie(movieRequest);
        List<String> value = fileUploadImpl.uploadFile(movieImage);
        movie.setStatus(Status.ACTIVE);
        movie.setImage(value.get(0));
        movie.setPublic_id(value.get(1));
        movieRepository.save(movie);
    }

    @Override
    public void updateMovie(int id, MovieRequest movieRequest, MultipartFile movieImage) throws IOException {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        if (movie.getStatus() != Status.ACTIVE) {
            throw new RuntimeException("Movie is not active");
        }
        if (movieRequest.getTitle() != null) movie.setTitle(movieRequest.getTitle());
        if (movieRequest.getDescription() != null) movie.setDescription(movieRequest.getDescription());
        if (movieRequest.getDirector() != null) movie.setDirector(movieRequest.getDirector());
        if (movieRequest.getReleaseDate() != null) movie.setReleaseDate(movieRequest.getReleaseDate());
        if (movieRequest.getActor() != null ) movie.setActors(movieRequest.getActor());
        if (movieRequest.getDuration() != 0) movie.setDuration(movieRequest.getDuration());
        if (movieImage != null && !movieImage.isEmpty()) {
            movie.setImage(fileUploadImpl.upDateFile(movieImage, movie.getPublic_id()));
        }
        movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(int id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setStatus(movie.getStatus().equals(Status.ACTIVE) ? Status.INACTIVE : Status.ACTIVE);
        movieRepository.save(movie);
    }

    @Override
    public List<MovieResponse> searchMovie(String keyWords) {
        return movieRepository.findByTitleContainingIgnoreCase(keyWords).stream().map(movieMapper::toMovieResponse).collect(Collectors.toList());
    }
}
