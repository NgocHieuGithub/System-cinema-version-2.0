package system.system_cinema.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieResponse {
    String id;
    String title;
    String genre;
    String description;
    String duration;
    String actor;
    String director;
    LocalDate releaseDate;
    String image;
    boolean isActive;
    Double averageRating;
}
