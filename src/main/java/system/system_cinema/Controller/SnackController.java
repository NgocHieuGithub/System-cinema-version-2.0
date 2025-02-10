package system.system_cinema.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    ISnackService snackService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("search-snacks")
    public ApiResponse<List<SnackResponse>> SearchSnacks(@RequestParam @NotNull String keyword) {
        return ApiResponse.<List<SnackResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(snackService.searchSnack(keyword))
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("get-snacks")
    public ApiResponse<List<Snack>> getSnacks() {
        return ApiResponse.<List<Snack>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(snackService.getSnacks())
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("create-snacks")
    public ApiResponse<?> CreateSnacks(@Valid @RequestBody SnackRequest request) {
        snackService.CreateSnack(request);
        return ApiResponse.<List<Snack>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("edit-snacks")
    public ApiResponse<?> EditSnacks(@Valid @RequestBody SnackRequest request) {
        snackService.EditSnack(request);
        return ApiResponse.<List<Snack>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("delete-snacks")
    public ApiResponse<?> DeleteSnacks(@RequestParam @NotNull int snackId) {
        snackService.DeleteSnack(snackId);
        return ApiResponse.<List<Snack>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }
}
