package system.system_cinema.Mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Response.StatusTicket;
import system.system_cinema.DTO.Response.TicketResponse;
import system.system_cinema.Model.Ticket;

@Component
@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketResponse toTicketResponse(Ticket ticket);

    StatusTicket toStatusTicketResponse(Ticket ticket);
}
