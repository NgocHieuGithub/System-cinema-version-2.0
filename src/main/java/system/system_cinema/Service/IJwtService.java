package system.system_cinema.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface IJwtService {
    String generateAccessToken(Map<String, Object> claims,String user);
    String generateRefreshToken(String user);

    String extractUserName(String token);

    String GenerateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}
