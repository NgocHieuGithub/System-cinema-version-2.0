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
    int movieId;  // ID của phim
    LocalDateTime startTime;  // Giờ bắt đầu chiếu
    LocalDateTime endTime;    // Giờ kết thúc chiếu
}

