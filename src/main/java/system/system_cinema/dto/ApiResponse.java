package system.system_cinema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message, error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;
    public ApiResponse(HttpStatus status, T data) {
        this.code = status.value();
        this.message = status.getReasonPhrase();
        this.data = data;
    }
    public ApiResponse(HttpStatus status) {
        this.code = status.value();
        this.message = status.getReasonPhrase();
    }
}
