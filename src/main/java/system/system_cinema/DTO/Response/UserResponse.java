    package system.system_cinema.DTO.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String name, email, phone, username, avt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Boolean isActive;
}
