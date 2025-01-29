package system.system_cinema.Mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.EditUserRequest;
import system.system_cinema.Model.User;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserMapper {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public User update(EditUserRequest editUserRequest, User user){
        if (editUserRequest.getEmail() != null){
            user.setEmail(editUserRequest.getEmail());
        }
        if (editUserRequest.getPassword() != null){
            user.setPassword(passwordEncoder.encode(editUserRequest.getPassword()));;
        }
        if (editUserRequest.getUsername() != null){
            user.setUsername(editUserRequest.getUsername());
        }
        if (editUserRequest.getName() != null){
            user.setName(editUserRequest.getName());
        }
        if (editUserRequest.getPhone() != null){
            user.setPhone(editUserRequest.getPhone());
        }
        return user;
    }
}
