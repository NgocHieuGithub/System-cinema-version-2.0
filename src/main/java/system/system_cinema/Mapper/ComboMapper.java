package system.system_cinema.Mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Response.ComboResponse;
import system.system_cinema.DTO.Response.InfoComboResponse;
import system.system_cinema.Model.Combo;
import system.system_cinema.Model.Combo_Detail;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface ComboMapper {
    ComboResponse toResponse(Combo combo);
}
