package system.system_cinema.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import system.system_cinema.Enum.Status;
import system.system_cinema.Enum.StatusOrder;
import system.system_cinema.Enum.StatusSeat;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatBooking extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "seat_id")
    Seat seat;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    Ticket ticket;

    @Enumerated(EnumType.STRING)
    StatusSeat status;

    @Transient
    LocalDateTime dateCreate;
    @Transient
    LocalDateTime dateUpdate;
}
