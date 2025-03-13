package system.system_cinema.service.serviceImplement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import system.system_cinema.domain.Params;
import system.system_cinema.dto.request.ParamsRequest;
import system.system_cinema.dto.response.ParamsResponse;
import system.system_cinema.mapper.ParamsMapper;
import system.system_cinema.repository.ParamsRepository;
import system.system_cinema.service.IParamsService;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ParamsService implements IParamsService {
    private final ParamsRepository paramsRepository;
    private final ParamsMapper paramsMapper;

    @Override
    public void createParams(ParamsRequest paramsRequest) {
        paramsRepository.save(paramsMapper.toParams(paramsRequest));
    }

    @Override
    public void updateParams(int id, ParamsRequest paramsRequest) {
        Params params = paramsMapper.toParams(paramsRequest);
        params.setId(id);
        paramsRepository.save(params);
    }

    @Override
    public void deleteParams(int id) {
        paramsRepository.deleteById(id);
    }

    @Override
    public List<ParamsResponse> getAllParams(Pageable pageable) {
        return paramsRepository.findAll(pageable).stream().map(paramsMapper::toParamsResponse).toList();
    }
}
