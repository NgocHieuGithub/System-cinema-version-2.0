package system.system_cinema.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LockSeatsRequest {
    @NotNull(message = "id seat must be not null")
    List<Integer> seatIds;
    @NotNull(message = "id showtime must be not null")
    int showtimeId;
    @NotNull(message = "id user must be not null")
    int userId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<DetailsFvB> snack, combo;
}
