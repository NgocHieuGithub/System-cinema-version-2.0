package system.system_cinema.service.serviceImplement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import system.system_cinema.dto.request.SnackRequest;
import system.system_cinema.dto.response.SnackResponse;
import system.system_cinema.constant.Status;
import system.system_cinema.mapper.SnackMapper;
import system.system_cinema.domain.Snack;
import system.system_cinema.repository.SnackRepository;
import system.system_cinema.service.ISnackService;

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
    public void createSnack(SnackRequest snackRequest) {
        Snack snack = Snack.builder()
                .price(snackRequest.getPrice())
                .name(snackRequest.getName())
                .img(snackRequest.getImg())
                .status(Status.ACTIVE)
                .build();
        snackRepository.save(snack);
    }

    @Override
    public void editSnack(SnackRequest snackRequest) {
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
    public void deleteSnack(int idSnack) {
        Snack snack = snackRepository.findById(idSnack).orElseThrow(() -> new RuntimeException("Snack not found"));
        snack.setStatus(snack.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE);
        snackRepository.save(snack);
    }
}
