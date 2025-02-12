package system.system_cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.system_cinema.domain.Ticket;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByUserIdOrderByDateCreateDesc(int userId);
}
