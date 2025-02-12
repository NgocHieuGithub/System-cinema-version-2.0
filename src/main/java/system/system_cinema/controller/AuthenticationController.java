package system.system_cinema.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.dto.ApiResponse;
import system.system_cinema.dto.request.*;
import system.system_cinema.dto.response.OTP_Response;
import system.system_cinema.dto.response.TokenResponse;
import system.system_cinema.service.IAuthenticateService;
import system.system_cinema.service.IUserService;
import java.io.UnsupportedEncodingException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    IAuthenticateService authenticateService;
    IUserService userService;

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ApiResponse.<TokenResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(authenticateService.authenticate(loginRequest))
                .build();
    }


    @PostMapping("/sign-up")
    public ApiResponse<TokenResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ApiResponse.<TokenResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(authenticateService.signUp(signUpRequest))
                .build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<TokenResponse> refreshToken(HttpServletRequest request) {
        return ApiResponse.<TokenResponse>builder()
                .message("Successful")
                .data(authenticateService.refreshToken(request.getHeader(AUTHORIZATION)))
                .build();
    }

    @PostMapping("/forgot-password")
    public ApiResponse<OTP_Response> forgotPassword(@Valid @RequestBody VerifyRequest request) throws MessagingException, UnsupportedEncodingException {
        return ApiResponse.<OTP_Response>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(authenticateService.createOTP(request))
                .build();
    }

    @PatchMapping("/update-password")
    public ApiResponse<?> updatePassword(@Valid @RequestBody EditUserRequest editUserRequest) {
        userService.updatePassword(editUserRequest);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }
}
