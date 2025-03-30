package system.system_cinema.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import system.system_cinema.dto.ApiResponse;
import system.system_cinema.dto.response.MovieResponse;
import system.system_cinema.dto.request.MovieRequest;
import system.system_cinema.service.IMovieService;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieController {
    IMovieService movieService;

    @GetMapping("/get-all")
    public ApiResponse<List<MovieResponse>> getAllMovies() {
        return ApiResponse.<List<MovieResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(movieService.getAllMovies())
                .build();
    }

    @GetMapping("/get")
    public ApiResponse<MovieResponse> getMovieById(@RequestParam @NotNull int id) {
        return ApiResponse.<MovieResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(movieService.getMovieById(id))
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<MovieResponse> createMovie(
            @Valid @RequestPart MovieRequest movieRequest,
            @Valid @RequestPart MultipartFile file) throws IOException {
        movieService.createMovie(movieRequest, file);
        return ApiResponse.<MovieResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<MovieResponse> updateMovie(
            @PathVariable @NotNull int id,
            @Valid @RequestPart MovieRequest movieRequest,
            @Valid @RequestPart(required = false) MultipartFile file) throws IOException {
        movieService.updateMovie(id, movieRequest, file);
        return ApiResponse.<MovieResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteMovie(@PathVariable @NotNull int id) {
        movieService.deleteMovie(id);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Movie deleted successfully")
                .build();
    }

    @GetMapping("/search-movie")
    public ApiResponse<List<MovieResponse>> searchMovie(@RequestParam @NotNull String keyword) {
        return ApiResponse.<List<MovieResponse>>builder()
                .code(HttpStatus.OK.value())
                .data(movieService.searchMovie(keyword))
                .build();
    }
}
