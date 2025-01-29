package system.system_cinema.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusTicket {
    String id, movie, room;
    boolean status;
    long price;
    LocalDateTime time;
}
