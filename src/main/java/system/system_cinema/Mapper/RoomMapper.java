package system.system_cinema.Mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Response.RoomResponse;
import system.system_cinema.DTO.Response.ShowtimeResponse;
import system.system_cinema.Model.Room;
import system.system_cinema.Model.Showtime;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomResponse toRoom(Room room);
}
