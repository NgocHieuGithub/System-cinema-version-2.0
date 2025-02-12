package system.system_cinema.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.system_cinema.dto.response.RoomResponse;
import system.system_cinema.domain.Room;

@Component
@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomResponse toRoom(Room room);
}
