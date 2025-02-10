package system.system_cinema.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComboRequest {
    @NotNull(message = "id combo must be not null")
    int id;
    @Pattern(regexp = "^[^/:*?\"<>|]+$", message = "Not valid")
    @NotNull(message = "Name combo require not null")
    @NotBlank(message = "Name combo require not blank")
    String name;
    @NotNull(message = "Image require not null")
    @NotBlank(message = "Image require not blank")
    @Pattern(regexp = "^[^/:*?\"<>|]+$", message = "Not valid")
    String img;
    @NotNull(message = "Price must be not null")
    int price;
    @NotNull(message = "Combo detail require not null")
    List<ComboDetailRequest> snacks;
}
