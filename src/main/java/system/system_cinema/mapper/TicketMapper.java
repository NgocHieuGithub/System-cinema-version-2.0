package system.system_cinema.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.system_cinema.dto.response.StatusTicket;
import system.system_cinema.dto.response.TicketResponse;
import system.system_cinema.domain.Ticket;

@Component
@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketResponse toTicketResponse(Ticket ticket);

    StatusTicket toStatusTicketResponse(Ticket ticket);
}
