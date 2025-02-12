package system.system_cinema.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import system.system_cinema.constant.Status;

import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Snack extends BaseEntity{

    String name, img;
    int price;

    @Enumerated(EnumType.STRING)
    Status status;

    @JsonIgnore
    @OneToMany(mappedBy = "snack")
    List<Combo_Detail> combo_details;

    @JsonIgnore
    @OneToMany(mappedBy = "snack")
    List<FoodOrder> food_beverage_orders;
}
