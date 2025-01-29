package system.system_cinema.Mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Response.ComboResponse;
import system.system_cinema.DTO.Response.InfoComboResponse;
import system.system_cinema.Model.Combo;
import system.system_cinema.Model.Combo_Detail;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComboMapper {
    public ComboResponse toResponse(Combo combo) {
        ComboResponse resp = ComboResponse.builder()
                .id(combo.getId())
                .name(combo.getName())
                .price(combo.getPrice())
                .img(combo.getImg())
                .active(combo.isActive())
                .build();
        List<InfoComboResponse> infoComboResponses = new ArrayList<>();
        for (Combo_Detail c : combo.getComboDetails()){
            InfoComboResponse infoComboResponse = InfoComboResponse.builder()
                    .idSnack(c.getSnack().getId())
                    .nameSnack(c.getSnack().getName())
                    .quantity(c.getQuantity())
                    .build();
            infoComboResponses.add(infoComboResponse);
        }
        resp.setInfoCombo(infoComboResponses);
        return resp;
    }
}
