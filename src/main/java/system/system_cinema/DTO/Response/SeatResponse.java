package system.system_cinema.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import system.system_cinema.Enum.StatusOrder;
import system.system_cinema.Enum.StatusSeat;
import system.system_cinema.Enum.TypeSeat;

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
