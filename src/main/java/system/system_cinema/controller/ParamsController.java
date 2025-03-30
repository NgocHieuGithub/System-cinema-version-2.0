package system.system_cinema.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.dto.ApiResponse;
import system.system_cinema.dto.request.ParamsRequest;
import system.system_cinema.dto.response.ParamsResponse;
import system.system_cinema.service.IParamsService;
import java.util.List;

@RestController
@RequestMapping("/params")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ParamsController {
    private final IParamsService paramsService;

    @GetMapping
    public ApiResponse<List<ParamsResponse>> getParams(@RequestParam(required = false) Pageable pageable) {
        return ApiResponse.<List<ParamsResponse>>builder()
                .data(paramsService.getAllParams(pageable))
                .build();
    }

    @PostMapping
    public ApiResponse<?> createParams(@RequestBody ParamsRequest paramsRequest) {
        paramsService.createParams(paramsRequest);
        return ApiResponse.builder()
                .code(200)
                .message("Done")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<?> updateParams(@PathVariable int id ,@RequestBody ParamsRequest paramsRequest) {
        paramsService.updateParams(id, paramsRequest);
        return ApiResponse.builder()
                .code(200)
                .message("Done")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteParams(@PathVariable int id) {
        paramsService.deleteParams(id);
        return ApiResponse.builder()
                .code(200)
                .message("Done")
                .build();
    }
}
