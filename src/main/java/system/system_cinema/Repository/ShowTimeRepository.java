package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.system_cinema.Model.Movie;
import system.system_cinema.Model.Showtime;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<Showtime, Integer> {

    List<Showtime> findByStartTimeBetweenOrderByMovie(LocalDateTime start, LocalDateTime end);

    List<Showtime> findByEndTimeAfter(LocalDateTime currentTime);

    List<Showtime> findByMovie(Movie movie);
}
