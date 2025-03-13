package system.system_cinema.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import system.system_cinema.constant.ParamType;
import system.system_cinema.constant.Topic;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Params extends BaseEntity{

    @Enumerated(EnumType.STRING)
    Topic topic;

    @Enumerated(EnumType.STRING)
    ParamType type;

    String name, description;

    int value;

}
