package system.system_cinema.Mapper;

import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Request.SeatBookingRequest;
import system.system_cinema.DTO.Response.SeatBookingResponse;
import system.system_cinema.Model.SeatBooking;

@Component
public class SeatBookingMapper {
    public SeatBookingResponse toSeatBookingResponse(SeatBooking seatBooking) {
        SeatBookingResponse response = new SeatBookingResponse();
        response.setId(seatBooking.getId());
        response.setSeatId(seatBooking.getSeat().getId());
        response.setTicketId(seatBooking.getTicket().getId());
        return response;
    }

    public SeatBooking toSeatBooking(SeatBookingRequest request) {
        return SeatBooking.builder().build();
    }
}
