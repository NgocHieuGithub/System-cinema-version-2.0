package system.system_cinema.DTO.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComboDetailRequest {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    int quantity;
}
