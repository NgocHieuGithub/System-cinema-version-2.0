package system.system_cinema.Service;

import jakarta.servlet.http.HttpServletRequest;
import system.system_cinema.DTO.Request.LockSeatsRequest;

public interface IVNPayService {
    public String HandleOrder(HttpServletRequest request, LockSeatsRequest lockSeatsRequest);
}
