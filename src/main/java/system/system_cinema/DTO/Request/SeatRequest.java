package system.system_cinema.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatRequest {
    @NotNull(message = "seat number must be not null")
    String seatNumber;
    @NotNull(message = "id room must be not null")
    int roomId;
    @NotNull(message = "type seat must be not null")
    int typeSeatId;
}
