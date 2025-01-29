package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.*;
import system.system_cinema.DTO.Response.OTP_Response;
import system.system_cinema.DTO.Response.TokenResponse;
import system.system_cinema.Service.ServiceImplement.AuthenticateServiceImp;
import system.system_cinema.Service.ServiceImplement.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticateServiceImp authenticateServiceImp;
    UserService userService;

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            return ApiResponse.<TokenResponse>builder()
                    .message("Successful")
                    .data(authenticateServiceImp.authenticate(loginRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<TokenResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }


    @PostMapping("/sign-up")
    public ApiResponse<TokenResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            return ApiResponse.<TokenResponse>builder()
                    .message("Successful")
                    .data(authenticateServiceImp.signUp(signUpRequest))
                    .build();
        } catch (Exception e){
            return ApiResponse.<TokenResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/refresh-token")
    public ApiResponse<TokenResponse> refreshToken(@RequestBody OneFieldRequest oneFieldRequest) {
        try {
            return ApiResponse.<TokenResponse>builder()
                    .message("Successful")
                    .data(authenticateServiceImp.refreshToken(oneFieldRequest.getInput()))
                    .build();
        } catch (Exception e){
            return ApiResponse.<TokenResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @PostMapping("forgot-password")
    public ApiResponse<OTP_Response> forgotPassword(@RequestBody VerifyRequest request) {
        try {
            return ApiResponse.<OTP_Response>builder()
                    .data(authenticateServiceImp.createOTP(request))
                    .build();
        } catch (Exception e){
            return ApiResponse.<OTP_Response>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @PatchMapping("/update-password")
    public ApiResponse<?> updatePassword(@RequestBody EditUserRequest editUserRequest) {
        try {
            System.out.println(editUserRequest.getPassword() + " " + editUserRequest.getId());
            userService.UpdatePassword(editUserRequest);
            return ApiResponse.builder()
                    .message("Successful")
                    .build();
        } catch (Exception e){
            return ApiResponse.builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
