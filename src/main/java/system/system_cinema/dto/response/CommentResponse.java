package system.system_cinema.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    int id;
    String content;
    String username;
    int rate;
    LocalDateTime dateCreate;
    LocalDateTime dateUpdate;
    int replyCount;
}
