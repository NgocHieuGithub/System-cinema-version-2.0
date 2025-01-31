package system.system_cinema.DTO.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowTimeAndRoomResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String movie;
    int id, roomId;
    LocalDateTime dateCreate,startTime, endTime;
    String nameRoom;
}
