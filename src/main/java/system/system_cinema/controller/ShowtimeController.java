package system.system_cinema.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.dto.request.ShowTimeRequestCreate;
import system.system_cinema.dto.ApiResponse;
import system.system_cinema.dto.response.ShowtimeResponse;
import system.system_cinema.mapper.ShowtimeMapper;
import system.system_cinema.repository.MovieRepository;
import system.system_cinema.repository.ShowTimeRepository;
import system.system_cinema.service.IShowTimeService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/showtimes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShowtimeController {
    IShowTimeService showtimeService;
    ShowTimeRepository showTimeRepository;
    MovieRepository movieRepository;
    ShowtimeMapper showtimeMapper;

    @PostMapping("/create")
    public ApiResponse<?> CreateShowTimeCtr(@RequestBody ShowTimeRequestCreate requestCreate){
        try {
            showtimeService.createShowTime(requestCreate);
            return ApiResponse.<ShowtimeResponse>builder()
                    .message("Showtime created successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<ShowtimeResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PatchMapping("/update")
    public ApiResponse<?> UpdateShowTimeCtr(@RequestParam int showTimeId, @RequestParam int roomId){
        try {
            showtimeService.updateShowTime(showTimeId, roomId);
            return ApiResponse.<ShowtimeResponse>builder()
                    .message("Showtime update successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<ShowtimeResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/get-list")
    public ApiResponse<?> GetShowTimeCtr(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
        try {
            return ApiResponse.<Map<String, List<String>>>builder()
                    .data(showtimeService.getListShowTime(date))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Map<String, List<String>>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/history")
    public ApiResponse<?> GetAll(@RequestParam int page) {
        try {
            return ApiResponse.<Map<?,?>>builder()
                    .data(showtimeService.getAllShowTimes(page))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<ShowtimeResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/delete")
    public ApiResponse<?> DeleteShowTimeCtr(@RequestParam int showTimeId){
        try {
            showtimeService.deleteShowTime(showTimeId);
            return ApiResponse.<ShowtimeResponse>builder()
                    .message("Showtime delete successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<ShowtimeResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
//    For user
    @GetMapping("/get-by-movie")
    public ApiResponse<List<?>> GetShowTimeByMovieId(@RequestParam int movieId){
        try {
            return ApiResponse.<List<?>>builder()
                    .data(showTimeRepository.findByMovie(movieRepository.findById(movieId).orElseThrow())
                            .stream()
                            .map(showtimeMapper::convertShowTimeClean).toList())
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<?>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
