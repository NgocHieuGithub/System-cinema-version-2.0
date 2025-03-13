package system.system_cinema.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.system_cinema.domain.Params;
import system.system_cinema.dto.request.ParamsRequest;
import system.system_cinema.dto.response.ParamsResponse;

@Component
@Mapper(componentModel = "spring")
public interface ParamsMapper {
    Params toParams(ParamsRequest params);
    ParamsResponse toParamsResponse(Params params);
}
