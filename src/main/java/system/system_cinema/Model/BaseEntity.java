package system.system_cinema.Model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    LocalDateTime dateCreate;
    LocalDateTime dateUpdate;

    @PrePersist
    public void prePersist() {
        dateCreate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        dateUpdate = LocalDateTime.now();
    }
}
