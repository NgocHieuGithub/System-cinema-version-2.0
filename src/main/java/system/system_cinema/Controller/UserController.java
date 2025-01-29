package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.EditUserRequest;
import system.system_cinema.DTO.Response.UserResponse;
import system.system_cinema.Service.ServiceImplement.UserService;
import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping("/get-my-infor")
    public ApiResponse<UserResponse> getMyInfor() {
        try {
            return ApiResponse.<UserResponse>builder()
                    .message("Successful")
                    .data(userService.GetUserDetails())
                    .build();
        } catch (Exception e){
            return ApiResponse.<UserResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/get-list-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<List<UserResponse>> getListUser() {
        try {
            return ApiResponse.<List<UserResponse>>builder()
                    .message("Successful")
                    .data(userService.GetAllUsers())
                    .build();
        } catch (Exception e){
            return ApiResponse.<List<UserResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PatchMapping("/edit-profile")
    public ApiResponse<?> editProfile(@RequestBody EditUserRequest editUserRequest) {
        try {
            userService.EditUser(editUserRequest);
            return ApiResponse.builder()
                    .message("Successful")
                    .build();
        } catch (Exception e){
            return ApiResponse.builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> lockAccount(@PathVariable int id) {
        try {
            userService.ActivateUser(id);
            return ApiResponse.builder()
                    .message("Successful")
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
