package system.system_cinema.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComboResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    int id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String name, img;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    int price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    boolean active;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<InfoComboResponse> infoCombo;
}
