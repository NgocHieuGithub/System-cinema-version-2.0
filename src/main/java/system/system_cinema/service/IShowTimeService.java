package system.system_cinema.service;

import system.system_cinema.dto.request.ShowTimeRequestCreate;

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
