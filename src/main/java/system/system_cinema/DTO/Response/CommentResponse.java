package system.system_cinema.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    String id;
    String userId;
    String movieId;
    String content;
    String username;
    int rate;  // Thêm trường đánh giá
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<CommentResponse> replies;
}
