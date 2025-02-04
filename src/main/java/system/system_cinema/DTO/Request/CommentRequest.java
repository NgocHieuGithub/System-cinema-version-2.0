package system.system_cinema.DTO.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentRequest {
    int user_id;
    int movieId;
    String content;
    int parentCommentId;
    int rate;
}
