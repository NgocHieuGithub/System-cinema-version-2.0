package system.system_cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import system.system_cinema.Enum.Status;

import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Combo extends BaseEntity{
    String name, img;

    int price;

    @Enumerated(EnumType.STRING)
    Status status;

    @OneToMany(mappedBy = "combo", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Combo_Detail> comboDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "combo")
    List<FoodBeverageOrder> foodBeverageOrders;
}
