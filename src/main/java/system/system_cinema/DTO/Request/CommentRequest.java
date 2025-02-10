package system.system_cinema.DTO.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentRequest {
    @NotNull(message = "id user must be not null")
    int user_id;
    @NotNull(message = "id movie must be not null")
    int movieId;
    @NotNull(message = "comment must be not null")
    @NotBlank(message = "comment must be not blank")
    String content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    int parentCommentId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    int rate;
}
