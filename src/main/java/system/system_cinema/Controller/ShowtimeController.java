package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.Request.ShowTimeRequestCreate;
import system.system_cinema.DTO.Request.ShowtimeRequest;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Response.ShowtimeResponse;
import system.system_cinema.Mapper.ShowtimeMapper;
import system.system_cinema.Repository.MovieRepository;
import system.system_cinema.Repository.ShowTimeRepository;
import system.system_cinema.Service.IShowTimeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/showtimes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShowtimeController {
    final IShowTimeService showtimeService;
    private final ShowTimeRepository showTimeRepository;
    private final MovieRepository movieRepository;
    private final ShowtimeMapper showtimeMapper;

    @PostMapping("/create/{cinemaHallId}")
    public ApiResponse<ShowtimeResponse> createShowtime(@PathVariable int cinemaHallId, @RequestBody ShowtimeRequest showtimeRequest) {
        try {
            return ApiResponse.<ShowtimeResponse>builder()
                    .message("Showtime created successfully")
                    .data(showtimeService.createShowtime(cinemaHallId, showtimeRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<ShowtimeResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
//    For admin
    @PostMapping("/admin/create")
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
    @PatchMapping("/admin/update")
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
    @GetMapping("/admin/get-list")
    public ApiResponse<?> GetShowTimeCtr(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date
    ) {
        System.out.println(date);
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

    @GetMapping("/admin/get-all-showtime")
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
    @GetMapping("/admin/delete")
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
    @GetMapping("/user/get-by-movieid")
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
