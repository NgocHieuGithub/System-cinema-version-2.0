package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.system_cinema.Model.Room;


@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    boolean existsByName(String name);
}
