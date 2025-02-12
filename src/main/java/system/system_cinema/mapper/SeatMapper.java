package system.system_cinema.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.system_cinema.dto.request.SeatRequest;
import system.system_cinema.dto.response.SeatResponse;
import system.system_cinema.domain.Seat;

@Component
@Mapper(componentModel = "spring")
public interface SeatMapper {
    SeatResponse toSeatResponse(Seat seat);
    Seat toSeat(SeatRequest request);
}
