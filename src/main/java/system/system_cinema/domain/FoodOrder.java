package system.system_cinema.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoodOrder extends BaseEntity{

    int quantity;

    @ManyToOne
    Combo combo;

    @ManyToOne
    Snack snack;

    @JsonIgnore
    @ManyToOne
    Ticket ticket;

    @Transient
    LocalDateTime dateCreate;
    @Transient
    LocalDateTime dateUpdate;
}
