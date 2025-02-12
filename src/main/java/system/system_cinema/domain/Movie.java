package system.system_cinema.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import system.system_cinema.constant.Status;
import system.system_cinema.constant.TypeMovie;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie extends BaseEntity{
    String title, description, actors, director, image, public_id;;

    int duration;

    LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    Status status;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    List<TypeMovie> type;

    @JsonIgnore
    @OneToMany(mappedBy = "movie")
    List<Comment> comments;
}
