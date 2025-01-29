package system.system_cinema.Service;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.LoginRequest;
import system.system_cinema.DTO.Request.SignUpRequest;
import system.system_cinema.DTO.Request.VerifyRequest;
import system.system_cinema.DTO.Response.OTP_Response;
import system.system_cinema.DTO.Response.TokenResponse;

import java.io.UnsupportedEncodingException;

@Service
public interface IAuthenticateService {
    TokenResponse authenticate(LoginRequest loginRequest);
    TokenResponse signUp(SignUpRequest signUpRequest);
    TokenResponse refreshToken(String refreshToken);
    OTP_Response createOTP(VerifyRequest request) throws MessagingException, UnsupportedEncodingException;
}
