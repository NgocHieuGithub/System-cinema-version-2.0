package system.system_cinema.DTO.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditUserRequest {
    @NotNull(message = "id must be not null")
    int id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String name, email, phone, username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Min(value = 8, message = "Require length min 8")
    String password;
}
