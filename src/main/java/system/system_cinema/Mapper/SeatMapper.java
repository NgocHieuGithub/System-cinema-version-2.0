package system.system_cinema.Mapper;

import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Request.SeatRequest;
import system.system_cinema.DTO.Response.SeatResponse;
import system.system_cinema.Model.Seat;

@Component
public class SeatMapper {
    public SeatResponse toSeatResponse(Seat seat) {
        return new SeatResponse();
    }

    public Seat toSeat(SeatRequest request) {
        return Seat.builder()
                .seatNumber(request.getSeatNumber())
                .build();
    }
}
