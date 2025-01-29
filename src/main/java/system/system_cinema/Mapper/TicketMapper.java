package system.system_cinema.Mapper;

import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Request.TicketRequest;
import system.system_cinema.DTO.Response.FvBHistory;
import system.system_cinema.DTO.Response.StatusTicket;
import system.system_cinema.DTO.Response.TicketResponse;
import system.system_cinema.Model.FoodBeverageOrder;
import system.system_cinema.Model.SeatBooking;
import system.system_cinema.Model.Ticket;

import java.util.ArrayList;
import java.util.List;

@Component
public class TicketMapper {
    public TicketResponse toTicketResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setId(ticket.getId());
        response.setPrice(ticket.getPrice());
        response.setDateBooking(ticket.getDateBooking());
        response.setMovie(ticket.getShowtime().getMovie().getTitle());
        List<String> info = new ArrayList<>();
        info.add(ticket.getShowtime().getStartTime().toString());
        info.add(ticket.getShowtime().getEndTime().toString());
        info.add(ticket.getShowtime().getCinemaHall().getName());
        response.setInfoShowTime(info);

        List<String> seat = new ArrayList<>();
        for (SeatBooking sb : ticket.getSeatBookings()) {
            seat.add(sb.getSeat().getSeatNumber());
        }
        response.setSeatIds(seat);
        List<FvBHistory> history = new ArrayList<>();
        if (ticket.getFoodBeverageOrders() != null){
            List<FoodBeverageOrder> orders = ticket.getFoodBeverageOrders();
            for (FoodBeverageOrder order : orders) {
                FvBHistory fbh = FvBHistory.builder().quantity(order.getQuantity()).build();
                if (order.getCombo() != null){
                    fbh.setName(order.getCombo().getName());
                } else {
                    fbh.setName(order.getSnack().getName());
                }
                history.add(fbh);
            }
            response.setFvb(history);
        }
        return response;
    }

    public StatusTicket toStatusTicketResponse(Ticket ticket) {
        StatusTicket statusTicket = new StatusTicket();
        statusTicket.setId(ticket.getId());
        statusTicket.setPrice(ticket.getPrice());
        statusTicket.setStatus(ticket.isPaid());
        statusTicket.setTime(ticket.getDateBooking());
        statusTicket.setMovie(ticket.getShowtime().getMovie().getTitle());
        statusTicket.setRoom(ticket.getShowtime().getCinemaHall().getName());
        return statusTicket;
    }
}
