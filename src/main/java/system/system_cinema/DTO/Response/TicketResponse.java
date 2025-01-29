package system.system_cinema.DTO.Response;

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
    private String id, movie;
    private int price;
    private LocalDateTime dateBooking;
    private List<String> infoShowTime;
    private List<String> seatIds;
    private List<FvBHistory> fvb;
}
