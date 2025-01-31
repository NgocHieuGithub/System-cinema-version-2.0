package system.system_cinema.Controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.EditUserRequest;
import system.system_cinema.DTO.Response.UserResponse;
import system.system_cinema.Model.User;
import system.system_cinema.Service.IUserService;
import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    IUserService userService;

    @GetMapping("/info")
    public ApiResponse<User> getMyInfo(@RequestParam int id) {
        return new ApiResponse<>(HttpStatus.OK, userService.GetUserDetails(id));
    }

    @GetMapping("/list-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<List<UserResponse>> getListUser() {
        return new ApiResponse<>(HttpStatus.OK, userService.GetAllUsers());
    }

    @PatchMapping("/edit")
    public ApiResponse<?> editProfile(@Valid @RequestBody EditUserRequest editUserRequest) {
        userService.EditUser(editUserRequest);
        return new ApiResponse<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<?> lockAccount(@RequestParam int id) {
        userService.ActivateUser(id);
        return new ApiResponse<>(HttpStatus.OK);
    }
}
