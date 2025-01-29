package system.system_cinema.DTO.Request;

import lombok.Data;

@Data
public class SeatBookingRequest {
    private String seatId;           // ID ghế
    private String ticketId;         // ID vé
}
