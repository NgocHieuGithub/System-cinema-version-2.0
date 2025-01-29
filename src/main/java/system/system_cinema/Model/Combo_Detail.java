package system.system_cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Combo_Detail extends BaseEntity{

    int quantity;

    @ManyToOne
    Snack snack;

    @JsonIgnore
    @ManyToOne
    Combo combo;

    @Transient
    LocalDateTime dateCreate;
    @Transient
    LocalDateTime dateUpdate;
}
