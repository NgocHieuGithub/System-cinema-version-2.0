package system.system_cinema.Mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Response.SnackResponse;
import system.system_cinema.Model.Snack;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SnackMapper {
    public SnackResponse toResponse(Snack snack) {
        return SnackResponse.builder()
                .id(snack.getId())
                .name(snack.getName())
                .price(snack.getPrice())
                .build();

    }
}
