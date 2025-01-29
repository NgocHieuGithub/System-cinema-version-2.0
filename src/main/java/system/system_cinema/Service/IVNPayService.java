package system.system_cinema.Service;

import jakarta.servlet.http.HttpServletRequest;
import system.system_cinema.DTO.Request.LockSeatsRequest;

public interface IVNPayService {
    public String CreateVNPayPayment(HttpServletRequest request, LockSeatsRequest lockSeatsRequest);
}
