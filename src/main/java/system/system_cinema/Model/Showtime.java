package system.system_cinema.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Showtime extends BaseEntity{

    LocalDateTime startTime, endTime;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    Movie movie;

    @ManyToOne
    @JoinColumn(name = "cinema_hall_id")
    Room room;

    @OneToMany(mappedBy = "showtime")
    @JsonIgnore
    List<Ticket> tickets;

    @Transient
    LocalDateTime dateCreate;
    @Transient
    LocalDateTime dateUpdate;
}