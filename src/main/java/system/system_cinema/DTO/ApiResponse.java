package system.system_cinema.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message, error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;
}
