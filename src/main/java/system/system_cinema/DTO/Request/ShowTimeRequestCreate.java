package system.system_cinema.DTO.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowTimeRequestCreate {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    int movieId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    LocalDateTime dateCreate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<DetailShowTime> timeSheet;
}
