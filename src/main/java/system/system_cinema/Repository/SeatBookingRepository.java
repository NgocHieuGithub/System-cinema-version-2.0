package system.system_cinema.Repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import system.system_cinema.Model.SeatBooking;

import java.util.List;

public interface SeatBookingRepository extends JpaRepository<SeatBooking, Integer> {
    List<SeatBooking> findByTicketId(int ticketId);
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query( " select sb " +
            " from SeatBooking sb" +
            " JOIN Seat s on sb.seat.id in :value1" +
            " JOIN Ticket t on sb.ticket.id = t.id" +
            " where t.showtime.id = :value2")
    List<SeatBooking> findBySeatIdInAndShowTimeId(List<Integer> value1, int value2);
}
