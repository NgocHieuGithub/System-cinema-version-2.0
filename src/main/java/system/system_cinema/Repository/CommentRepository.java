package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import system.system_cinema.Model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c FROM Comment c WHERE c.movie.id = :movieId AND c.parentComment IS NULL")
    List<Comment> findRootCommentsByMovieId(@Param("movieId") String movieId);
}
