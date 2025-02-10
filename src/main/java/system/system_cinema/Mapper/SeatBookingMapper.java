package system.system_cinema.Mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Response.SeatBookingResponse;
import system.system_cinema.Model.SeatBooking;

@Component
@Mapper(componentModel = "spring")
public interface SeatBookingMapper {
    SeatBookingResponse toSeatBookingResponse(SeatBooking seatBooking);
}
