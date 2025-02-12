package system.system_cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import system.system_cinema.dto.response.SeatResponse;
import system.system_cinema.domain.Seat;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    @Query("SELECT new system.system_cinema.dto.response.SeatResponse(" +
            "s.id, s.seatNumber, s.type, " +
            "CASE WHEN sb.status Is not null AND t.status = system.system_cinema.constant.StatusOrder.SUCCESS THEN system.system_cinema.constant.StatusSeat.OCCUPIED ELSE system.system_cinema.constant.StatusSeat.UNOCCUPIED END" +
            ") " +
            "FROM Seat s " +
            "LEFT JOIN SeatBooking sb ON s.id = sb.seat.id " +
            "LEFT JOIN Ticket t ON sb.ticket.id = t.id AND t.showtime.id = :showtimeId " +
            "WHERE s.room.id = (SELECT st.room.id FROM Showtime st WHERE st.id = :showtimeId)")
    List<SeatResponse> findSeatsWithTypeAndStatusByShowTime(@Param("showtimeId") int showtimeId);

}
