package system.system_cinema.DTO.Request;

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
public class VerifyRequest {
    @Pattern(regexp = "^[^/:*?\"<>|]+$", message = "Not valid")
    @NotNull(message = "Username must be not null")
    @NotBlank(message = "Username must be not blank")
    String username;
    @Pattern(regexp = "^[^/:*?\"<>|]+$", message = "Not valid")
    @NotNull(message = "Email must be not null")
    @NotBlank(message = "Email must be not blank")
    String email;
}
