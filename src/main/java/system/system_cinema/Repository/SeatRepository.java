package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import system.system_cinema.DTO.Response.SeatResponse;
import system.system_cinema.Model.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

    @Query("SELECT new system.system_cinema.DTO.Response.SeatResponse(" +
            "s.id, s.seatNumber, s.type ," +
            "COALESCE(sb.status, 'available')) " +
            "FROM Seat s " +
            "LEFT JOIN SeatBooking sb ON s.id = sb.seat.id AND sb.ticket.showtime.id = :showtimeId " +
            "WHERE s.room.id = (SELECT st.room.id FROM Showtime st WHERE st.id = :showtimeId)")
    List<SeatResponse> findSeatsWithTypeAndStatusByShowTime(@Param("showtimeId") int showtimeId);
}
