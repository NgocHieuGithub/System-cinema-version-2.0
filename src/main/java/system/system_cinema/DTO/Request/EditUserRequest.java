package system.system_cinema.DTO.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    String name, email, phone, username, password;
}
