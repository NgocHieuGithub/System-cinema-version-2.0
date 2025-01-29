package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.system_cinema.Model.Snack;

import java.util.List;

@Repository
public interface SnackRepository extends JpaRepository<Snack, Integer> {
    List<Snack> findByNameContainingIgnoreCase(String name);
    @Query("SELECT s FROM Snack s WHERE s.active = true")
    List<Snack> findAllActiveSnacks();
}
