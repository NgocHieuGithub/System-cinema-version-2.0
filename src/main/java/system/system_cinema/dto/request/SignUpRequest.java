package system.system_cinema.dto.request;

import jakarta.validation.constraints.*;
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
    String username;
    @Pattern(regexp = "^[^/:*?\"<>|]+$", message = "Not valid")
    @NotBlank(message = "Value must be not blank")
    @NotNull(message = "Value must be not null")
    @Size(min = 8, max = 12, message = "Password needs a minimum of 8 characters")
    String password;
    @Pattern(regexp = "^[^/:*?\"<>|]+$", message = "Not valid")
    @NotBlank(message = "Value must be not blank")
    @NotNull(message = "Value must be not null")
    String email;
}
