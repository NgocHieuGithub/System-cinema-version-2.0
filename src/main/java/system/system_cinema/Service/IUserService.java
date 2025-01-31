package system.system_cinema.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.EditUserRequest;
import system.system_cinema.DTO.Response.UserResponse;
import system.system_cinema.Model.User;

import java.util.List;

@Service
public interface IUserService {
    User GetUserDetails(int id);
    List<UserResponse> GetAllUsers();
    void EditUser(EditUserRequest editUserRequest);
    void UpdatePassword(EditUserRequest editUserRequest);
    void ActivateUser(int info);
    UserDetailsService getUserDetailsService();
}
