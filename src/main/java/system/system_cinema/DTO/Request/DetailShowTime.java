package system.system_cinema.DTO.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetailShowTime {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    LocalDateTime timeStart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    int roomId;
}
