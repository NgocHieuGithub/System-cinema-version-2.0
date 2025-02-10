package system.system_cinema.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import system.system_cinema.Enum.Status;
import system.system_cinema.Enum.TypeMovie;
import system.system_cinema.Enum.TypeSeat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieResponse {
    int id;
    String title;
    List<TypeMovie> type;
    String description;
    int duration;
    String actor;
    String director;
    LocalDate releaseDate;
    String image;
    Status status;
    Double averageRating;
}
