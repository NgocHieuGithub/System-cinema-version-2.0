package system.system_cinema.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.system_cinema.Model.Movie;
import system.system_cinema.Model.Showtime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<Showtime, Integer> {

    List<Showtime> findByStartTimeBetweenOrderByMovie(LocalDateTime start, LocalDateTime end);

    List<Showtime> findByEndTimeAfter(LocalDateTime currentTime);

//    List<Showtime> findAllBy(Sort sort);
    List<Showtime> findByMovie(Movie movie);
}
