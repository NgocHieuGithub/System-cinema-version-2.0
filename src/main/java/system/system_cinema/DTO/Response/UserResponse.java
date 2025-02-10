    package system.system_cinema.DTO.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import system.system_cinema.Enum.Status;

    @Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    int id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String name, email, phone, username, avt;
    Status status;
}
