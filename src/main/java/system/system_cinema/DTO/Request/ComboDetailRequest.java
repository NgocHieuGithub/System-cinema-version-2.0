package system.system_cinema.DTO.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComboDetailRequest {
    @NotNull(message = "id must be not null")
    int id;
    @NotNull(message = "quantity must be not null")
    @Min(value = 1, message = "Min value must be 1")
    @Max(value = 5, message = "Max value must be 5")
    int quantity;
}
