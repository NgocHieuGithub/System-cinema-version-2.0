package system.system_cinema.Service;

import system.system_cinema.DTO.Request.ShowTimeRequestCreate;
import system.system_cinema.DTO.Request.ShowtimeRequest;
import system.system_cinema.DTO.Response.ShowtimeResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IShowTimeService {
    void createShowTime(ShowTimeRequestCreate requestCreate);
    void updateShowTime(int showTimeId, int roomId);
    void deleteShowTime(int showTimeId);
    Map<String, List<String>> getListShowTime(LocalDate date);
    Map<?,?> getAllShowTimes(int page);
}
