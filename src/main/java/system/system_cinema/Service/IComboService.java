package system.system_cinema.Service;

import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.ComboRequest;
import system.system_cinema.DTO.Response.ComboResponse;

import java.util.List;
import java.util.Map;

@Service
public interface IComboService {
    public Map<String, Object> GetComboAndSnack();
    public List<ComboResponse> getCombo();
    public void CreateCombo(ComboRequest comboRequest);
    public void EditCombo(ComboRequest comboRequest);
    public void DeleteCombo(int comboId);
}
