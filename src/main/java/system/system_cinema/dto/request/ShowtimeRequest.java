package system.system_cinema.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowtimeRequest {
    @NotNull(message = "id movie must be not null")
    int movieId;
    LocalDateTime startTime;
    LocalDateTime endTime;
}

