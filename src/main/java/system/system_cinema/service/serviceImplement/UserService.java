package system.system_cinema.service.serviceImplement;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import system.system_cinema.dto.request.EditUserRequest;
import system.system_cinema.dto.response.UserResponse;
import system.system_cinema.constant.Status;
import system.system_cinema.mapper.UserMapper;
import system.system_cinema.domain.User;
import system.system_cinema.repository.UserRepository;
import system.system_cinema.service.IUserService;

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
    public User getUserDetails(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findUsers();
    }

    @Override
    public void editUser(EditUserRequest editUserRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(editUserRequest.getId()).orElseThrow(()->new RuntimeException("Not found user"));
        if (!user.getUsername().equals(username)) {
            throw new RuntimeException("Jwt not match information user");
        }
        userRepository.save(userMapper.toUser(editUserRequest));
    }

    @Override
    public void updatePassword(EditUserRequest editUserRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(editUserRequest.getId()).orElseThrow(()->new RuntimeException("Not found user"));
        if (!user.getUsername().equals(username)) {
            throw new RuntimeException("Jwt not match information user");
        }
        user.setPassword(getPasswordEncoder.encode(editUserRequest.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void activateUser(int id) {
        User user = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Not found user"));
        user.setStatus(user.getStatus().equals(Status.ACTIVE) ? Status.INACTIVE : Status.ACTIVE);
        userRepository.save(user);
    }

    @Override
    public UserDetailsService getUserDetailsService() {
        return username -> userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
