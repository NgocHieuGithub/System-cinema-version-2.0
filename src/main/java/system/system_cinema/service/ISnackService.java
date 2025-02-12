package system.system_cinema.service;

import org.springframework.stereotype.Service;
import system.system_cinema.dto.request.SnackRequest;
import system.system_cinema.dto.response.SnackResponse;
import system.system_cinema.domain.Snack;

import java.util.List;

@Service
public interface ISnackService {
    List<SnackResponse> searchSnack(String keyWord);
    List<Snack> getSnacks();
    void createSnack(SnackRequest snackRequest);
    void editSnack(SnackRequest snackRequest);
    void deleteSnack(int idSnack);
}
