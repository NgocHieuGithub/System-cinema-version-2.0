package system.system_cinema.service.serviceImplement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import system.system_cinema.dto.request.ComboDetailRequest;
import system.system_cinema.dto.request.ComboRequest;
import system.system_cinema.dto.response.ComboResponse;
import system.system_cinema.constant.Status;
import system.system_cinema.mapper.ComboMapper;
import system.system_cinema.domain.Combo;
import system.system_cinema.domain.Combo_Detail;
import system.system_cinema.domain.Snack;
import system.system_cinema.repository.ComboRepository;
import system.system_cinema.repository.SnackRepository;
import system.system_cinema.service.IComboService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComboService implements IComboService {
    SnackRepository snackRepository;
    ComboRepository comboRepository;
    ComboMapper comboMapper;

    @Override
    public Map<String, Object> getComboAndSnack() {
        List<Snack> snacks = snackRepository.findAllActiveSnacks();
        List<ComboResponse> combos = comboRepository.findAllActiveCombo().stream().map(comboMapper::toResponse).toList();
        Map<String, Object> result = new HashMap<>();
        result.put("snack", snacks);
        result.put("combo", combos);
        return result;
    }

    @Override
    public List<ComboResponse> getCombo() {
        return comboRepository.findAll().stream().map(comboMapper::toResponse).toList();
    }

    @Override
    public void createCombo(ComboRequest comboRequest) {
        Combo combo = Combo.builder()
                .name(comboRequest.getName())
                .price(comboRequest.getPrice())
                .status(Status.ACTIVE)
                .img(comboRequest.getImg())
                .build();
        List<Combo_Detail> combo_details = new ArrayList<>();
        for (ComboDetailRequest r : comboRequest.getSnacks()){
            Snack snack = snackRepository.findById(r.getId()).orElseThrow(() -> new RuntimeException("Snack not found"));
            Combo_Detail combo_Detail = Combo_Detail.builder()
                    .snack(snack)
                    .quantity(r.getQuantity())
                    .build();
            combo_details.add(combo_Detail);
        }
        combo.setComboDetails(combo_details);
        for (Combo_Detail combo_detail : combo_details){
            combo_detail.setCombo(combo);
        }
        comboRepository.save(combo);
    }

    @Override
    public void editCombo(ComboRequest comboRequest) {
        Combo combo = Combo.builder()
                .id(comboRequest.getId())
                .name(comboRequest.getName())
                .price(comboRequest.getPrice())
                .img(comboRequest.getImg())
                .status(Status.ACTIVE)
                .build();
        List<Combo_Detail> combo_details = new ArrayList<>();
        for (ComboDetailRequest r : comboRequest.getSnacks()){
            Snack snack = snackRepository.findById(r.getId()).orElseThrow(() -> new RuntimeException("Snack not found"));
            Combo_Detail combo_Detail = Combo_Detail.builder()
                    .snack(snack)
                    .quantity(r.getQuantity())
                    .build();
            combo_details.add(combo_Detail);
        }
        combo.setComboDetails(combo_details);
        for (Combo_Detail combo_detail : combo_details){
            combo_detail.setCombo(combo);
        }
        comboRepository.save(combo);
    }

    @Override
    public void deleteCombo(int comboId) {
        Combo combo = comboRepository.findById(comboId).orElseThrow(() -> new RuntimeException("Combo not found"));
        combo.setStatus(combo.getStatus().equals(Status.ACTIVE) ? Status.INACTIVE : Status.ACTIVE);
        comboRepository.save(combo);
    }
}
