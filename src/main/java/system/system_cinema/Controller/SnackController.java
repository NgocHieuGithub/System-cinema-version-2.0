package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.SnackRequest;
import system.system_cinema.DTO.Response.SnackResponse;
import system.system_cinema.Model.Snack;
import system.system_cinema.Service.ISnackService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("snacks")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SnackController {
    private final ISnackService snackService;

    // For Admin
    @GetMapping("search-snacks")
    public ApiResponse<List<SnackResponse>> SearchSnacks(@RequestParam String keyword) {
        try {
            return ApiResponse.<List<SnackResponse>>builder()
                    .message("Successful")
                    .data(snackService.searchSnack(keyword))
                    .build();
        } catch (Exception e){
            return ApiResponse.<List<SnackResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @GetMapping("get-snacks")
    public ApiResponse<List<Snack>> getSnacks() {
        try {
            return ApiResponse.<List<Snack>>builder()
                    .message("Successful")
                    .data(snackService.getSnacks())
                    .build();
        } catch (Exception e){
            return ApiResponse.<List<Snack>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @PostMapping("create-snacks")
    public ApiResponse<?> CreateSnacks(@RequestBody SnackRequest request) {
        try {
            snackService.CreateSnack(request);
            return ApiResponse.<List<Snack>>builder()
                    .message("Successful")
                    .build();
        } catch (Exception e){
            return ApiResponse.<List<Snack>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @PutMapping("edit-snacks")
    public ApiResponse<?> EditSnacks(@RequestBody SnackRequest request) {
        try {
            snackService.EditSnack(request);
            return ApiResponse.<List<Snack>>builder()
                    .message("Successful")
                    .build();
        } catch (Exception e){
            return ApiResponse.<List<Snack>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("delete-snacks")
    public ApiResponse<?> DeleteSnacks(@RequestParam int snackId) {
        try {
            snackService.DeleteSnack(snackId);
            return ApiResponse.<List<Snack>>builder()
                    .message("Successful")
                    .build();
        } catch (Exception e){
            return ApiResponse.<List<Snack>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
