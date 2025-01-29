package system.system_cinema.Service.ServiceImplement;

import lombok.RequiredArgsConstructor;
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
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final FileUpload fileUploadImpl;

    @Override
    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(movie -> {
                    double averageRating = movie.getComments().stream()
                            .filter(comment -> comment.getParentComment() == null)
                            .mapToInt(Comment::getRate)
                            .average()
                            .orElse(0.0);

                    MovieResponse movieResponse = movieMapper.toMovieResponse(movie);
                    movieResponse.setAverageRating(averageRating); // Thiết lập averageRating
                    return movieResponse;
                })
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

        movie.setTitle(movieRequest.getTitle());
        movie.setDescription(movieRequest.getDescription());
        movie.setDirector(movieRequest.getDirector());
        movie.setReleaseDate(movieRequest.getReleaseDate());
        movie.setActors(movieRequest.getActor());
        movie.setDuration(movieRequest.getDuration());
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

    @Override
    public MovieResponse getMovieWithAverageRating(int id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // Tính toán trung bình đánh giá dựa trên các bình luận
        double averageRating = movie.getComments().stream()
                .filter(comment -> comment.getParentComment() == null) // Only root comments
                .mapToInt(Comment::getRate)
                .average()
                .orElse(0.0);

        MovieResponse movieResponse = movieMapper.toMovieResponse(movie);
        movieResponse.setAverageRating(averageRating);  // Cập nhật điểm đánh giá trung bình vào MovieResponse
        return movieResponse;
    }
}
