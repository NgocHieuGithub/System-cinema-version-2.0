package system.system_cinema.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.dto.ApiResponse;
import system.system_cinema.dto.request.ComboRequest;
import system.system_cinema.dto.response.ComboResponse;
import system.system_cinema.service.IComboService;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("combo")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComboController {
    IComboService comboService;

    // For User
    @GetMapping("get-combo-and-snack")
    public ApiResponse<Map<String,Object>> getComboAndSnack() {
        return ApiResponse.<Map<String, Object>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(comboService.getComboAndSnack())
                .build();
    }

    // For Admin
    @GetMapping("get")
    public ApiResponse<List<ComboResponse>> getCombo() {
        return ApiResponse.<List<ComboResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(comboService.getCombo())
                .build();
    }

    @PostMapping("create")
    public ApiResponse<?> createCombo(@Valid @RequestBody ComboRequest comboRequest) {
        comboService.createCombo(comboRequest);
        return ApiResponse.<List<ComboResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }

    @PatchMapping("edit")
    public ApiResponse<?> editCombo(@Valid @RequestBody ComboRequest comboRequest) {
        comboService.editCombo(comboRequest);
        return ApiResponse.<List<ComboResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }

    @DeleteMapping("delete")
    public ApiResponse<?> deleteCombo(@RequestParam @NotNull int comboId) {
        comboService.deleteCombo(comboId);
        return ApiResponse.<List<ComboResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }
}
