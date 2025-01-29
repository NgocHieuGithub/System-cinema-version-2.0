package system.system_cinema.DTO.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditUserRequest {
    int id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String name, email, phone, username, password;
}
