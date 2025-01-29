package system.system_cinema.Service.ServiceImplement;

import io.jsonwebtoken.Claims;
import jakarta.mail.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.LoginRequest;
import system.system_cinema.DTO.Request.SignUpRequest;
import system.system_cinema.DTO.Request.VerifyRequest;
import system.system_cinema.DTO.Response.OTP_Response;
import system.system_cinema.DTO.Response.TokenResponse;
import system.system_cinema.Enum.Role;
import system.system_cinema.Enum.Status;
import system.system_cinema.Model.User;
import system.system_cinema.Repository.UserRepository;
import system.system_cinema.Service.IAuthenticateService;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticateServiceImp implements IAuthenticateService {
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    JwtService jwtService;
    PasswordEncoder getPasswordEncoder;
    MailService mailService;

    @Override
    public TokenResponse authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        var user = userRepository.findAccount(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(loginRequest.getUsername() + " not found"));
        if (user.getStatus().equals(Status.INACTIVE)){
            throw new UsernameNotFoundException("Account has not been activated");
        }
        return TokenResponse
                .builder()
                .access_token(jwtService.generateAccessToken(getClaims(user), user.getUsername()))
                .refresh_token(jwtService.generateRefreshToken(user.getUsername()))
                .build();
    }


    @Override
    public TokenResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername()) || userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email or Username already exists");
        }
        User user = User
                .builder()
                .username(signUpRequest.getUsername())
                .password(getPasswordEncoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .dateCreate(LocalDateTime.now())
                .status(Status.ACTIVE)
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return TokenResponse
                .builder()
                .access_token(jwtService.generateAccessToken(getClaims(user), user.getUsername()))
                .refresh_token(jwtService.generateRefreshToken(user.getUsername()))
                .build();
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        Date expiration = jwtService.extractClaim(refreshToken, Claims::getExpiration);
        if (expiration == null || expiration.before(new Date(System.currentTimeMillis()))) {
            throw new RuntimeException("Refresh token expired");
        }
        String username = jwtService.extractClaim(refreshToken, Claims::getSubject);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return TokenResponse
                .builder()
                .access_token(jwtService.generateAccessToken(getClaims(user), user.getUsername()))
                .build();
    }

    @Override
    public OTP_Response createOTP(VerifyRequest verifyRequest) throws MessagingException, UnsupportedEncodingException {
        String id = String.valueOf(userRepository.findByEmailAndUsername(verifyRequest.getEmail(), verifyRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found user") ).getId());
        String code = mailService.sendEmail(verifyRequest.getEmail());
        return OTP_Response
                .builder()
                .code(code)
                .expiration(LocalDateTime.now())
                .id(id)
                .build();
    }

    private Map<String, Object> getClaims(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("role", user.getRole());
        return map;
    }
}
