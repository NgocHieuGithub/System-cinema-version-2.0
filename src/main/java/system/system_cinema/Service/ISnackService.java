package system.system_cinema.Service;

import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.SnackRequest;
import system.system_cinema.DTO.Response.SnackResponse;
import system.system_cinema.Model.Snack;

import java.util.List;

@Service
public interface ISnackService {
    public List<SnackResponse> searchSnack(String keyWord);
    public List<Snack> getSnacks();
    public void CreateSnack(SnackRequest snackRequest);
    public void EditSnack(SnackRequest snackRequest);
    public void DeleteSnack(int idSnack);
}
