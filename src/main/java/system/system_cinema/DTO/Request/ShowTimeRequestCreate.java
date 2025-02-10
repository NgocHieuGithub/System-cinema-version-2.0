package system.system_cinema.DTO.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "id movie must be not null")
    int movieId;
    @NotNull(message = "Date create must be not null")
    LocalDateTime dateCreate;
    @NotNull(message = "seat number must be not null")
    List<DetailShowTime> timeSheet;
}
