package system.system_cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import system.system_cinema.Enum.Role;
import system.system_cinema.Enum.Status;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity implements UserDetails {
    String username, password, email, name, phone, avt;
    @Enumerated(EnumType.STRING)
    Status status;
    @Enumerated(EnumType.STRING)
    Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Ticket> tickets;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Comment> comments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}