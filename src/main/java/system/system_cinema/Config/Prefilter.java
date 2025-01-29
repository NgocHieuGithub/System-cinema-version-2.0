package system.system_cinema.Config;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import system.system_cinema.Service.IJwtService;
import system.system_cinema.Service.IUserService;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class Prefilter extends OncePerRequestFilter {
    IJwtService jwtService;
    IUserService userService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            final String jwt = authHeader.substring(7);
            final String userName = jwtService.extractUserName(jwt);
            if (StringUtils.isNotEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.getUserDetailsService().loadUserByUsername(userName);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                }
            }
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
