package system.system_cinema.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.dto.ApiResponse;
import system.system_cinema.dto.request.SnackRequest;
import system.system_cinema.dto.response.SnackResponse;
import system.system_cinema.domain.Snack;
import system.system_cinema.service.ISnackService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("snacks")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SnackController {
    ISnackService snackService;

    @GetMapping("search-snacks")
    public ApiResponse<List<SnackResponse>> searchSnacks(@RequestParam @NotNull String keyword) {
        return ApiResponse.<List<SnackResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(snackService.searchSnack(keyword))
                .build();
    }

    @GetMapping("get-snacks")
    public ApiResponse<List<Snack>> getSnacks() {
        return ApiResponse.<List<Snack>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(snackService.getSnacks())
                .build();
    }

    @PostMapping("create-snacks")
    public ApiResponse<?> createSnacks(@Valid @RequestBody SnackRequest request) {
        snackService.createSnack(request);
        return ApiResponse.<List<Snack>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }

    @PutMapping("edit-snacks")
    public ApiResponse<?> editSnacks(@Valid @RequestBody SnackRequest request) {
        snackService.editSnack(request);
        return ApiResponse.<List<Snack>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }

    @DeleteMapping("delete-snacks")
    public ApiResponse<?> deleteSnacks(@RequestParam @NotNull int snackId) {
        snackService.deleteSnack(snackId);
        return ApiResponse.<List<Snack>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }
}
