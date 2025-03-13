package system.system_cinema.service.serviceImplement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import system.system_cinema.dto.response.*;
import system.system_cinema.mapper.TicketMapper;
import system.system_cinema.repository.*;
import system.system_cinema.service.ITicketService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketService implements ITicketService {
    TicketRepository ticketRepository;
    UserRepository userRepository;
    ShowTimeRepository showtimeRepository;
    TicketMapper ticketMapper;
    BookingRepository bookingRepository;
    MovieRepository movieRepository;

    @Override
    public List<StatusTicket> getStatusTickets() {
        return ticketRepository.findAll(Sort.by(Sort.Direction.DESC, "dateBooking")).stream().map(ticketMapper::toStatusTicketResponse).toList();
    }

    @Override
    public List<TicketResponse> getTicketsByUser(int userId) {
        return ticketRepository.findByUserIdOrderByDateCreateDesc(userId).stream()
                .map(ticketMapper::toTicketResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<StatisticPriceMonthResponse> statisticPriceMonth() {
        return bookingRepository.getMonthlyStatistics().stream().map(
                record -> new StatisticPriceMonthResponse(
                        (String) record[0],
                        ((BigDecimal) record[1]).longValue(),
                        ((Long) record[2])
                )
        ).collect(Collectors.toList());
    }

    @Override
    public List<StatisticUserTicket> statisticUserTicket() {
        return userRepository.statisticTicketUser().stream().map(
                record -> new StatisticUserTicket(
                        (String) record[0],
                        (Long) record[1]
                )
        ).collect(Collectors.toList());
    }

    @Override
    public List<StatisticMovieRevenue> statisticMovieRevenue() {
        return movieRepository.StatisticMovieRevenue().stream().map(
                record -> new StatisticMovieRevenue(
                        (String) record[0],
                        ((BigDecimal) record[1]).longValue()
                )
        ).collect(Collectors.toList());
    }

}
