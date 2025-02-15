package system.system_cinema.service;

import jakarta.mail.MessagingException;
import system.system_cinema.dto.request.LoginRequest;
import system.system_cinema.dto.request.SignUpRequest;
import system.system_cinema.dto.request.VerifyRequest;
import system.system_cinema.dto.response.OTP_Response;
import system.system_cinema.dto.response.TokenResponse;

import java.io.UnsupportedEncodingException;

public interface IAuthenticateService {
    TokenResponse authenticate(LoginRequest loginRequest);
    TokenResponse signUp(SignUpRequest signUpRequest);
    TokenResponse refreshToken(String refreshToken);
    void createOTP(VerifyRequest request) ;
}
