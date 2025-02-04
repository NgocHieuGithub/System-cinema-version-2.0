package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    private final IComboService comboService;

    // For User
    @GetMapping("get-combo-and-snack")
    public ApiResponse<Map<String,Object>> GetComboAndSnack() {
        try {
            return ApiResponse.<Map<String, Object>>builder()
                    .message("Successful")
                    .data(comboService.GetComboAndSnack())
                    .build();
        } catch (Exception e){
            return ApiResponse.<Map<String, Object>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    // For Admin
    @GetMapping("get")
    public ApiResponse<List<ComboResponse>> GetCombo() {
        try {
            return ApiResponse.<List<ComboResponse>>builder()
                    .message("Successful")
                    .data(comboService.getCombo())
                    .build();
        } catch (Exception e){
            return ApiResponse.<List<ComboResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PostMapping("create")
    public ApiResponse<?> CreateCombo(@RequestBody ComboRequest comboRequest) {
        try {
            comboService.CreateCombo(comboRequest);
            return ApiResponse.<List<ComboResponse>>builder()
                    .message("Successful")
                    .build();
        } catch (Exception e){
            return ApiResponse.<List<ComboResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PutMapping("edit")
    public ApiResponse<?> EditCombo(@RequestBody ComboRequest comboRequest) {
        try {
            comboService.EditCombo(comboRequest);
            return ApiResponse.<List<ComboResponse>>builder()
                    .message("Successful")
                    .build();
        } catch (Exception e){
            return ApiResponse.<List<ComboResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("delete")
    public ApiResponse<?> DeleteCombo(@RequestParam int comboId) {
        try {
            comboService.DeleteCombo(comboId);
            return ApiResponse.<List<ComboResponse>>builder()
                    .message("Successful")
                    .build();
        } catch (Exception e){
            return ApiResponse.<List<ComboResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
