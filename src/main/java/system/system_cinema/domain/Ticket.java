package system.system_cinema.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import system.system_cinema.constant.StatusOrder;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket extends BaseEntity{

    int price;
    @Enumerated(EnumType.STRING)
    StatusOrder status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "showtime_id")
    Showtime showtime;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    List<SeatBooking> seatBookings;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    List<FoodOrder> foodBeverageOrders;

    @Transient
    LocalDateTime dateUpdate;
}
