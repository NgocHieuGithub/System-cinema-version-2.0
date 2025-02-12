package system.system_cinema.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.dto.ApiResponse;
import system.system_cinema.dto.request.EditUserRequest;
import system.system_cinema.dto.response.UserResponse;
import system.system_cinema.domain.User;
import system.system_cinema.service.IUserService;
import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    IUserService userService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/info")
    public ApiResponse<User> getMyInfo(@RequestParam @NotNull int id) {
        return new ApiResponse<>(HttpStatus.OK, userService.getUserDetails(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list-user")
    public ApiResponse<List<UserResponse>> getListUser() {
        return new ApiResponse<>(HttpStatus.OK, userService.getAllUsers());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PatchMapping("/edit")
    public ApiResponse<?> editProfile(@Valid @RequestBody EditUserRequest editUserRequest) {
        userService.editUser(editUserRequest);
        return new ApiResponse<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete")
    public ApiResponse<?> lockAccount(@RequestParam @NotNull int id) {
        userService.activateUser(id);
        return new ApiResponse<>(HttpStatus.OK);
    }
}
