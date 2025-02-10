package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.system_cinema.DTO.Response.UserResponse;
import system.system_cinema.Model.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query("select new system.system_cinema.DTO.Response.UserResponse(u.id, u.name, u.email, u.phone,u.username, u.avt, u.status)" +
            "from User u")
    List<UserResponse> findUsers();

    Optional<User> findByEmailAndUsername(String email, String username);

    @Query(nativeQuery = true,value = """
            SELECT u.username, COALESCE(COUNT(DISTINCT sb.id), 0) AS So_ve_mua
            FROM Database_SysCinema.user as u
            left join Database_SysCinema.ticket AS t on u.id = t.user_id
            left join Database_SysCinema.seat_booking as sb on t.id = sb.ticket_id
            join Database_SysCinema.user_roles as ur on u.id = ur.user_id and ur.role_id = 3
            group by u.username
            order by So_ve_mua asc
            limit 3;""")
    List<Object[]> statisticTicketUser();
}
