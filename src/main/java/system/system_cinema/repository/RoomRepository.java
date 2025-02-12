package system.system_cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.system_cinema.domain.Room;


@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    boolean existsByName(String name);
}
