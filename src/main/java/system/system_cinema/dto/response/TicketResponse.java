package system.system_cinema.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketResponse {
    int id;
    String nameMovie;
    int price;
    LocalDateTime dateBooking;
    List<String> infoShowTime;
    List<String> seatIds;
    List<FvBHistory> fvb;
}
