package system.system_cinema.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComboResponse {
    String id, name, img;
    int price;
    boolean active;
    List<InfoComboResponse> infoCombo;
}
