package system.system_cinema.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import system.system_cinema.constant.StatusOrder;

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
