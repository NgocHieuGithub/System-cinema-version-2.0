package system.system_cinema.service;

import org.springframework.data.domain.Pageable;
import system.system_cinema.dto.request.ParamsRequest;
import system.system_cinema.dto.response.ParamsResponse;

import java.util.List;

public interface IParamsService {
    void createParams(ParamsRequest paramsRequest);
    void updateParams(int id, ParamsRequest paramsRequest);
    void deleteParams(int id);
    List<ParamsResponse> getAllParams(Pageable paramPageable);
}
