package system.system_cinema.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface IJwtService {
    String generateToken(UserDetails userDetails, long time);
    String extractUserName(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
