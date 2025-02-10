package system.system_cinema.Mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Response.SnackResponse;
import system.system_cinema.Model.Snack;

@Component
@Mapper(componentModel = "spring")
public interface SnackMapper {
    SnackResponse toResponse(Snack snack);
}
