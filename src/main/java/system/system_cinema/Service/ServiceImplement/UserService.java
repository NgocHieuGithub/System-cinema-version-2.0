package system.system_cinema.Service.ServiceImplement;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.EditUserRequest;
import system.system_cinema.DTO.Response.UserResponse;
import system.system_cinema.Enum.Status;
import system.system_cinema.Mapper.UserMapper;
import system.system_cinema.Model.User;
import system.system_cinema.Repository.UserRepository;
import system.system_cinema.Service.IUserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder getPasswordEncoder;

    // Xem thong tin chi tiet user
    @Override
    public User GetUserDetails(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    @Override
    public List<UserResponse> GetAllUsers() {
        return userRepository.findUsers();
    }

    @Override
    public void EditUser(EditUserRequest editUserRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(editUserRequest.getId()).orElseThrow(()->new RuntimeException("Not found user"));
        if (!user.getUsername().equals(username)) {
            throw new RuntimeException("Jwt not match information user");
        }
        userRepository.save(userMapper.update(editUserRequest, user));
    }

    @Override
    public void UpdatePassword(EditUserRequest editUserRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(editUserRequest.getId()).orElseThrow(()->new RuntimeException("Not found user"));
        if (!user.getUsername().equals(username)) {
            throw new RuntimeException("Jwt not match information user");
        }
        user.setPassword(getPasswordEncoder.encode(editUserRequest.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void ActivateUser(int id) {
        User user = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Not found user"));
        user.setStatus(user.getStatus().equals(Status.ACTIVE) ? Status.INACTIVE : Status.ACTIVE);
        userRepository.save(user);
    }

    @Override
    public UserDetailsService getUserDetailsService() {
        return username -> userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
