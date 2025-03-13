package system.system_cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.system_cinema.domain.Params;

@Repository
public interface ParamsRepository extends JpaRepository<Params, Integer> {
}
