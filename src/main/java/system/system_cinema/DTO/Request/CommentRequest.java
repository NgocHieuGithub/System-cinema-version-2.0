package system.system_cinema.DTO.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentRequest {
    int movieId;
    String content;
    int parentCommentId;
    String username;
    int rate;  // Thêm trường đánh giá
}
