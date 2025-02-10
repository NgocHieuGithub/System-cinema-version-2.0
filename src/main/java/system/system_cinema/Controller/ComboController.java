package system.system_cinema.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.ComboRequest;
import system.system_cinema.DTO.Response.ComboResponse;
import system.system_cinema.Service.IComboService;
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
    public ApiResponse<Map<String,Object>> GetComboAndSnack() {
        return ApiResponse.<Map<String, Object>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(comboService.GetComboAndSnack())
                .build();
    }

    // For Admin
    @GetMapping("get")
    public ApiResponse<List<ComboResponse>> GetCombo() {
        return ApiResponse.<List<ComboResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(comboService.getCombo())
                .build();
    }

    @PostMapping("create")
    public ApiResponse<?> CreateCombo(@Valid @RequestBody ComboRequest comboRequest) {
        comboService.CreateCombo(comboRequest);
        return ApiResponse.<List<ComboResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }

    @PatchMapping("edit")
    public ApiResponse<?> EditCombo(@Valid @RequestBody ComboRequest comboRequest) {
        comboService.EditCombo(comboRequest);
        return ApiResponse.<List<ComboResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }

    @DeleteMapping("delete")
    public ApiResponse<?> DeleteCombo(@RequestParam @NotNull int comboId) {
        comboService.DeleteCombo(comboId);
        return ApiResponse.<List<ComboResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }
}
