package system.system_cinema.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.system_cinema.dto.response.SnackResponse;
import system.system_cinema.domain.Snack;

@Component
@Mapper(componentModel = "spring")
public interface SnackMapper {
    SnackResponse toResponse(Snack snack);
}
