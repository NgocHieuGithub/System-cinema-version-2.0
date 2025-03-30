package system.system_cinema.service.serviceImplement;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import system.system_cinema.dto.request.LoginRequest;
import system.system_cinema.dto.request.SignUpRequest;
import system.system_cinema.dto.request.VerifyRequest;
import system.system_cinema.dto.response.TokenResponse;
import system.system_cinema.constant.Role;
import system.system_cinema.constant.Status;
import system.system_cinema.domain.User;
import system.system_cinema.repository.UserRepository;
import system.system_cinema.service.IAuthenticateService;
import system.system_cinema.service.IKafkaProducerService;

@Slf4j(topic = "Authenticate-service")
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticateService implements IAuthenticateService {
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    JwtService jwtService;
    PasswordEncoder getPasswordEncoder;
    MailService mailService;
    IKafkaProducerService kafkaProducerService;

    @NonFinal
    @Value("${jwt.valid-duration}")
    long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    long REFRESHABLE_DURATION;

    // Sign in
    @Override
    public TokenResponse authenticate(LoginRequest loginRequest) {
        log.info("------------Start authenticate--------------");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        var user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(loginRequest.getUsername() + " not found"));
        if (user.getStatus().equals(Status.INACTIVE)){
            throw new UsernameNotFoundException("Account has not been activated");
        }
        log.info("------------Finish authenticate--------------");
        return TokenResponse
                .builder()
                .access_token(jwtService.generateToken(user, VALID_DURATION))
                .refresh_token(jwtService.generateToken(user, REFRESHABLE_DURATION))
                .build();
    }

    // Sign up
    @Override
    public TokenResponse signUp(SignUpRequest signUpRequest) {
        log.info("------------Start sign up new user--------------");
        if (userRepository.existsByUsername(signUpRequest.getUsername()) || userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email or Username already exists");
        }
        User user = User
                .builder()
                .username(signUpRequest.getUsername())
                .password(getPasswordEncoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .status(Status.ACTIVE)
                .role(Role.USER)
                .build();
        userRepository.save(user);
        log.info("------------Finish sign up new user--------------");
        return TokenResponse
                .builder()
                .access_token(jwtService.generateToken(user, VALID_DURATION))
                .refresh_token(jwtService.generateToken(user, REFRESHABLE_DURATION))
                .build();
    }

    // Refresh token
    @Override
    public TokenResponse refreshToken(String refreshToken) {
        log.info("------------Start refresh token--------------");
        String username = jwtService.extractUserName(refreshToken);
        User user = userRepository.findByUsername(username).orElseThrow(()-> new EntityNotFoundException("User not found"));
        if (user.getStatus().equals(Status.ACTIVE)) {
            log.info("------------End refresh token--------------");
            return TokenResponse
                    .builder()
                    .access_token(jwtService.generateToken(user, VALID_DURATION))
                    .refresh_token(jwtService.generateToken(user, REFRESHABLE_DURATION))
                    .build();
        } else {
           throw new RuntimeException("Invalid refresh token");
        }
    }


    @Override
    public void createOTP(VerifyRequest verifyRequest) {
        log.info("------------Start send email OTP--------------");
        userRepository.findByEmailAndUsername(verifyRequest.getEmail(), verifyRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found user"));
        kafkaProducerService.SendMessage("send_mail","change_password" , verifyRequest.getEmail());
        log.info("------------Finish send email OTP--------------");
    }

}
