package system.system_cinema.service;

import org.springframework.stereotype.Service;
import system.system_cinema.dto.request.ComboRequest;
import system.system_cinema.dto.response.ComboResponse;

import java.util.List;
import java.util.Map;

@Service
public interface IComboService {
    Map<String, Object> getComboAndSnack();
    List<ComboResponse> getCombo();
    void createCombo(ComboRequest comboRequest);
    void editCombo(ComboRequest comboRequest);
    void deleteCombo(int comboId);
}
