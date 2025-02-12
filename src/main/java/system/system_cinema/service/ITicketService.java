package system.system_cinema.service;

import system.system_cinema.dto.response.*;

import java.util.List;

public interface ITicketService {
    List<StatusTicket> getStatusTickets();
    List<TicketResponse> getTicketsByUser(int userId);
    List<StatisticPriceMonthResponse> statisticPriceMonth();
    List<StatisticUserTicket> statisticUserTicket();
    List<StatisticMovieRevenue> statisticMovieRevenue();
}
