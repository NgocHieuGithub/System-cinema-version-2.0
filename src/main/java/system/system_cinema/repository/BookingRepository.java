package system.system_cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.system_cinema.domain.Showtime;
import system.system_cinema.domain.Ticket;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Ticket, Integer>
{
    List<Ticket> findByShowtime(Showtime showtime);

        @Query(value = """
            SELECT\s
                m.month,
                COALESCE(SUM(DISTINCT t.price), 0) AS total_price,
                COALESCE(COUNT(DISTINCT sb.id), 0) AS total_tickets_sold
            FROM\s
                (SELECT DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 2 MONTH), '%Y-%m') AS month
                 UNION ALL
                 SELECT DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 MONTH), '%Y-%m')
                 UNION ALL
                 SELECT DATE_FORMAT(CURDATE(), '%Y-%m')) m
            LEFT JOIN Database_SysCinema.ticket t\s
            ON DATE_FORMAT(t.date_booking, '%Y-%m') = m.month
            LEFT JOIN Database_SysCinema.seat_booking sb\s
            ON sb.ticket_id = t.id
            GROUP BY m.month
            ORDER BY m.month
       \s""", nativeQuery = true)
        List<Object[]> getMonthlyStatistics();

}
