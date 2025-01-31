package system.system_cinema.DTO.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowtimeRequest {
    int movieId;
    LocalDateTime startTime;
    LocalDateTime endTime;
}

