package system.system_cinema.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetailShowTime {
    @NotNull(message = "timeStart must be not null")
    LocalDateTime timeStart;
    @NotNull(message = "id room must be not null")
    int roomId;
}
