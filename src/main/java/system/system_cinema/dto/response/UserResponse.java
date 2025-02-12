    package system.system_cinema.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import system.system_cinema.constant.Status;

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
