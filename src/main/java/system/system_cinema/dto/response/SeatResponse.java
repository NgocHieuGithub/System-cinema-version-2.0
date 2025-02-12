package system.system_cinema.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import system.system_cinema.constant.StatusSeat;
import system.system_cinema.constant.TypeSeat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatResponse {
    int seatId;
    String seatNumber;
    TypeSeat typeName;
    StatusSeat status;
}
