package system.system_cinema.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import system.system_cinema.dto.request.EditUserRequest;
import system.system_cinema.dto.response.UserResponse;
import system.system_cinema.domain.User;

import java.util.List;

@Service
public interface IUserService {
    User getUserDetails(int id);
    List<UserResponse> getAllUsers();
    void editUser(EditUserRequest editUserRequest);
    void updatePassword(EditUserRequest editUserRequest);
    void activateUser(int info);
    UserDetailsService getUserDetailsService();
}
