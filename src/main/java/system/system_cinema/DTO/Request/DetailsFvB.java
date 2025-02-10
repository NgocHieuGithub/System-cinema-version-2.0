package system.system_cinema.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetailsFvB {
    @NotNull(message = "id must be not null")
    int id;
    @NotNull(message = "quantity must be not null")
    int quantity;
}
