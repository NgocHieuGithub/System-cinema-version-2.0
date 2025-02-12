package system.system_cinema.dto.request;

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
public class LoginRequest {
    @Pattern(regexp = "^[^/:*?\"<>|]+$", message = "Not valid")
    @NotBlank(message = "Username must be not blank")
    @NotNull(message = "Username must be not null")
    String username;
    @Pattern(regexp = "^[^/:*?\"<>|]+$", message = "Not valid")
    @NotBlank(message = "Password must be not blank")
    @NotNull(message = "Password must be not null")
    String password;
}
