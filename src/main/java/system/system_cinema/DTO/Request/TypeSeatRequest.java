package system.system_cinema.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeSeatRequest {
    @NotNull(message = "type name must be not null")
    @NotBlank(message = "type name must be not blank")
    String typeName;
    @NotNull(message = "price must be not null")
    int price;
}
