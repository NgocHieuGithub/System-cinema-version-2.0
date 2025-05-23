package system.system_cinema.dto.request;

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
public class CommentEditRequest {
    @NotNull(message = "id user must be not null")
    int user_id;
    @NotNull(message = "id comment must be not null")
    int commentId;
    @NotNull(message = "comment must be not null")
    @NotBlank(message = "comment must be not blank")
    String content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    int rate;
}
