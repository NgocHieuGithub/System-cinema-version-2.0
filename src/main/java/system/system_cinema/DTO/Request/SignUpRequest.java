package system.system_cinema.DTO.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpRequest {
    @Pattern(regexp = "^[^/:*?\"<>|]+$", message = "Not valid")
    @NotBlank(message = "Value must be not blank")
    @NotNull(message = "Value must be not null")
    String username, password, email;
}
