package system.system_cinema.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String generateToken(UserDetails userDetails, long time);
    String extractUserName(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
