package system.system_cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.system_cinema.domain.Combo;

import java.util.List;

@Repository
public interface ComboRepository extends JpaRepository<Combo,Integer> {
    @Query("SELECT c FROM Combo c WHERE c.status = 'ACTIVE'")
    List<Combo> findAllActiveCombo();
}
