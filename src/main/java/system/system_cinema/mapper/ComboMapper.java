package system.system_cinema.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.system_cinema.dto.response.ComboResponse;
import system.system_cinema.domain.Combo;

@Component
@Mapper(componentModel = "spring")
public interface ComboMapper {
    ComboResponse toResponse(Combo combo);
}
