package system.system_cinema.service;

import jakarta.servlet.http.HttpServletRequest;
import system.system_cinema.dto.request.LockSeatsRequest;

public interface IVNPayService {
    String handleOrder(HttpServletRequest request, LockSeatsRequest lockSeatsRequest);
}
