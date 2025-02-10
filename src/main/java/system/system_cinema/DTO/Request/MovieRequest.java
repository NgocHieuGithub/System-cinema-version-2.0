package system.system_cinema.DTO.Request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Require not null")
    @NotBlank(message = "Require not blank")
    String title, genre, description, actor, director;
    @NotNull(message = "ReleaseDate require not null")
    LocalDate releaseDate;
    @NotNull(message = "Duration require not null")
    int duration;
}
