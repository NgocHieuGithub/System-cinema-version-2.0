package system.system_cinema.Service.ServiceImplement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.SnackRequest;
import system.system_cinema.DTO.Response.SnackResponse;
import system.system_cinema.Enum.Status;
import system.system_cinema.Mapper.SnackMapper;
import system.system_cinema.Model.Snack;
import system.system_cinema.Repository.SnackRepository;
import system.system_cinema.Service.ISnackService;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SnackService implements ISnackService {
    private final SnackRepository snackRepository;
    private final SnackMapper snackMapper;

    @Override
    public List<SnackResponse> searchSnack(String keyWord) {
        return snackRepository.findByNameContainingIgnoreCase(keyWord).stream().map(snackMapper::toResponse).toList();
    }

    @Override
    public List<Snack> getSnacks() {
        return snackRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public void CreateSnack(SnackRequest snackRequest) {
        Snack snack = Snack.builder()
                .price(snackRequest.getPrice())
                .name(snackRequest.getName())
                .img(snackRequest.getImg())
                .status(Status.ACTIVE)
                .build();
        snackRepository.save(snack);
    }

    @Override
    public void EditSnack(SnackRequest snackRequest) {
        Snack snack = Snack.builder()
                .id(snackRequest.getId())
                .price(snackRequest.getPrice())
                .name(snackRequest.getName())
                .img(snackRequest.getImg())
                .status(Status.ACTIVE)
                .build();
        snackRepository.save(snack);
    }

    @Override
    public void DeleteSnack(int idSnack) {
        Snack snack = snackRepository.findById(idSnack).orElseThrow(() -> new RuntimeException("Snack not found"));
        snack.setStatus(snack.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE);
        snackRepository.save(snack);
    }
}
