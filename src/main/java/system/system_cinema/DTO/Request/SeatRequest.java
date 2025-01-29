package system.system_cinema.DTO.Request;

import lombok.Data;

@Data
public class SeatRequest {
    private String seatNumber;       // Số ghế
    private int cinemaHallId;     // ID phòng chiếu
    private int typeSeatId;       // ID loại ghế
}
