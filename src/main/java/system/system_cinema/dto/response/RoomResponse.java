package system.system_cinema.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import system.system_cinema.constant.Status;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomResponse {
    int id;
    String name;
    Status status;
    List<ShowtimeResponse> showtimes;
}
