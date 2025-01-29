package system.system_cinema.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponse {
    private String seatId;
    private String seatNumber;
    private String typeName;
    private int seatPrice;
    private String seatStatus;
}
