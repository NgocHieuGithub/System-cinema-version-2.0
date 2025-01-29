package system.system_cinema.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import system.system_cinema.Enum.Status;

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
