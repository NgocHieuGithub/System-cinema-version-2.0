package system.system_cinema.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import system.system_cinema.constant.TypeMovie;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieRequest {
    @NotNull(message = "Require not null")
    @NotBlank(message = "Require not blank")
    String title, description, actor, director;
    @NotNull(message = "ReleaseDate require not null")
    LocalDate releaseDate;
    @NotNull(message = "Duration require not null")
    int duration;
    @NotNull(message = "Type must be not null")
    List<TypeMovie> type;
}
