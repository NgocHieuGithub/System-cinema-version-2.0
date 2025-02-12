package system.system_cinema.repository;

import org.springframework.data.jpa.repository.Query;
import system.system_cinema.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByTitleContainingIgnoreCase(String title);

    @Query(nativeQuery = true, value = """
            SELECT m.title, COALESCE(SUM(DISTINCT t.price), 0) AS Doanh_thu
            FROM Database_SysCinema.movie as m
            left join Database_SysCinema.showtime AS s on m.id = s.movie_id
            left join Database_SysCinema.ticket AS t on s.id = t.showtime_id
            group by m.title
            order by Doanh_thu desc
            limit 3;""")
    List<Object[]> StatisticMovieRevenue();
}
