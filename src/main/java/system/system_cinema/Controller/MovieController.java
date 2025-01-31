package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Response.MovieResponse;
import system.system_cinema.DTO.Request.MovieRequest;

import system.system_cinema.Service.IMovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieController {
    final IMovieService movieService;

    @GetMapping("/get-all")
    public ApiResponse<List<MovieResponse>> getAllMovies() {
        try {
            return ApiResponse.<List<MovieResponse>>builder()
                    .message("Successful")
                    .data(movieService.getAllMovies())
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<MovieResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/get")
    public ApiResponse<MovieResponse> getMovieById(@RequestParam int id) {
        try {
            return ApiResponse.<MovieResponse>builder()
                    .message("Successful")
                    .data(movieService.getMovieById(id))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<MovieResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/create")
    public ApiResponse<MovieResponse> createMovie(@RequestPart MovieRequest movieRequest, @RequestPart MultipartFile file) {
        try {
            movieService.createMovie(movieRequest, file);
            return ApiResponse.<MovieResponse>builder()
                    .message("Successful")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<MovieResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PutMapping("/update/{id}")
    public ApiResponse<MovieResponse> updateMovie(@PathVariable int id, @RequestPart MovieRequest movieRequest, @RequestPart(required = false) MultipartFile file) {
        try {
            movieService.updateMovie(id, movieRequest, file);
            return ApiResponse.<MovieResponse>builder()
                    .message("Successful")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<MovieResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteMovie(@PathVariable int id) {
        try {
            movieService.deleteMovie(id);
            return ApiResponse.builder()
                    .message("Movie deleted successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/search-movie")
    public ApiResponse<List<MovieResponse>> searchMovie(@RequestParam String keyword) {
        try {
            return ApiResponse.<List<MovieResponse>>builder()
                    .data(movieService.searchMovie(keyword))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<MovieResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
