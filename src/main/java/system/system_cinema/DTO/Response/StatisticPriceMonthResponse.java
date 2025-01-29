package system.system_cinema.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatisticPriceMonthResponse {
    String time;
    long totalPrice, totalTicket;
}
