package system.system_cinema.Service.ServiceImplement;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import system.system_cinema.Service.IJwtService;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtService implements IJwtService {

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;
    @Override
    public String generateAccessToken(Map<String, Object> claims, String username) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .signWith(secretKeySpec, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken(String username) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(
                        Instant.now().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .signWith(secretKeySpec, SignatureAlgorithm.HS256)
                .compact();
    }



    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String GenerateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SIGNER_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }
}
