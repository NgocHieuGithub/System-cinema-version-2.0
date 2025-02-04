package system.system_cinema.Service;

import system.system_cinema.DTO.Request.CommentRequest;
import system.system_cinema.DTO.Response.CommentResponse;
import java.util.List;

public interface ICommentService {
    CommentResponse addComment(CommentRequest commentRequest);
    List<CommentResponse> getCommentsByMovie(int movieId);
    CommentResponse updateComment(CommentRequest request);
    void deleteComment(int commentId);
}
