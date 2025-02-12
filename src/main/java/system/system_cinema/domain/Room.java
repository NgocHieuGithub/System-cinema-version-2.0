package system.system_cinema.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import system.system_cinema.constant.Status;

import java.util.List;
@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room extends BaseEntity{

    String name;

    @Enumerated(EnumType.STRING)
    Status status;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    List<Seat> seats;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Showtime> showtimes;

    int quantity;
}
