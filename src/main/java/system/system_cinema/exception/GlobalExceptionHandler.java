package system.system_cinema.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import system.system_cinema.dto.ApiResponse;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ApiResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage()).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ApiResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleGeneralException(Exception ex) {
        return ApiResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage()).build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleValidationException(MethodArgumentNotValidException ex) {
        return ApiResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage()).build();
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<?> handleExpiredJwtException(AuthorizationDeniedException ex) {
        return ApiResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage()).build();
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleValidationException(RuntimeException ex) {
        return ApiResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage()).build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleValidationException(MissingServletRequestParameterException ex) {
        return ApiResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage()).build();
    }
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<?> handleExpiredJWT(ExpiredJwtException ex) {
        return ApiResponse.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage()).build();
    }
}

