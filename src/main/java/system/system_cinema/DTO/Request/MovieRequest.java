package system.system_cinema.DTO.Request;


import lombok.*;
import lombok.experimental.FieldDefaults;
import system.system_cinema.Enum.Status;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieRequest {
    String title, genre, description, actor, director;
    LocalDate releaseDate;
    int duration;
}
