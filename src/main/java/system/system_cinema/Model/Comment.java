package system.system_cinema.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment extends BaseEntity{

    @ManyToOne
    User user;
    @ManyToOne
    Movie movie;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> replies;

    @Column(nullable = false)
    String content;

    @Column(nullable = false)
    int rate;

}
