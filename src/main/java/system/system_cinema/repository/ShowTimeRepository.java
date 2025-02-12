package system.system_cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.system_cinema.domain.Movie;
import system.system_cinema.domain.Showtime;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<Showtime, Integer> {

    List<Showtime> findByStartTimeBetweenOrderByMovie(LocalDateTime start, LocalDateTime end);

    List<Showtime> findByEndTimeAfter(LocalDateTime currentTime);

    List<Showtime> findByMovie(Movie movie);
}
