package system.system_cinema.Mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Request.EditUserRequest;
import system.system_cinema.Model.User;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(EditUserRequest editUserRequest);
}
