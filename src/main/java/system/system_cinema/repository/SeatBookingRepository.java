package system.system_cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.system_cinema.domain.SeatBooking;

public interface SeatBookingRepository extends JpaRepository<SeatBooking, Integer> {

}
