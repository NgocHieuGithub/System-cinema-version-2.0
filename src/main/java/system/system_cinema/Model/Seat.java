package system.system_cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import system.system_cinema.Enum.TypeSeat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Seat extends BaseEntity{
    String seatNumber;

    @ManyToOne
    Room room;

    @JsonIgnore
    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    List<SeatBooking> seatBookings;

    @Enumerated(EnumType.STRING)
    TypeSeat type;

    @Transient
    LocalDateTime dateCreate;

    @Transient
    LocalDateTime dateUpdate;
}
