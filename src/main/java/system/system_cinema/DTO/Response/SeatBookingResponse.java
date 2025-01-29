package system.system_cinema.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatBookingResponse {
    private String id;
    private String seatId;
    private String ticketId;
}
