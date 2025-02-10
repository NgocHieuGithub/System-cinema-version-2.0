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
public class SnackRequest {
    @NotNull(message = "id must be not null")
    int id;
    @Pattern(regexp = "^[^/:*?\"<>|]+$", message = "Not valid")
    @NotBlank(message = "Snack name must be not blank")
    @NotNull(message = "Snack name must be not null")
    String name;
    @Pattern(regexp = "^[^/:*?\"<>|]+$", message = "Not valid")
    @NotBlank(message = "Img must be not blank")
    @NotNull(message = "Img must be not null")
    String img;
    @NotNull(message = "price must be not null")
    int price;
}
