package system.system_cinema.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.system_cinema.dto.request.EditUserRequest;
import system.system_cinema.domain.User;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(EditUserRequest editUserRequest);
}
