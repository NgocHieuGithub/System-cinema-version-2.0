package system.system_cinema.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import system.system_cinema.Enum.StatusOrder;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusTicket {
    int id;
    String room;
    String movie;
    StatusOrder status;
    long price;
    LocalDateTime time;
}
