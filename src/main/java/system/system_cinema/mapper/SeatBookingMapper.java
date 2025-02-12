package system.system_cinema.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.system_cinema.dto.response.SeatBookingResponse;
import system.system_cinema.domain.SeatBooking;

@Component
@Mapper(componentModel = "spring")
public interface SeatBookingMapper {
    SeatBookingResponse toSeatBookingResponse(SeatBooking seatBooking);
}
