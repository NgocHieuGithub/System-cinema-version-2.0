package system.system_cinema.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import system.system_cinema.Model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Modifying
    @Query("UPDATE Comment c SET c.parentComment.id = NULL WHERE c.parentComment.id = :parentId")
    void makeRepliesIndependent(@Param("parentId") Integer parentId);

    @Query("SELECT c FROM Comment c WHERE c.parentComment IS NULL and c.movie.id = :value ORDER BY c.dateCreate DESC")
    List<Comment> findParentComments(Pageable pageable, int value);

    @Query("SELECT c FROM Comment c WHERE c.parentComment.id = :parentId ORDER BY c.dateCreate ASC")
    List<Comment> findRepliesByParentId(Pageable pageable, @Param("parentId") Integer parentId);
}
