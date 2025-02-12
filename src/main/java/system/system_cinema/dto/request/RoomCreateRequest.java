package system.system_cinema.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomCreateRequest {
    @NotNull(message = "Name must be not null")
    @NotBlank(message = "Name must be not blank")
    String name;

    @NotNull(message = "Number of VIP seats is required")
    @Min(value = 0, message = "Number of VIP seats must be 0 or greater")
    Integer vipSeats;

    @NotNull(message = "Number of normal seats is required")
    @Min(value = 0, message = "Number of normal seats must be 0 or greater")
    Integer normalSeats;

    @NotNull(message = "Number of couple seats is required")
    @Min(value = 0, message = "Number of couple seats must be 0 or greater")
    Integer coupleSeats;
}
