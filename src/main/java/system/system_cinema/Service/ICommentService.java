package system.system_cinema.Service;

import system.system_cinema.DTO.Request.CommentRequest;
import system.system_cinema.DTO.Response.CommentResponse;

import java.util.List;

public interface ICommentService {
    CommentResponse addComment(int userId, CommentRequest commentRequest);
    CommentResponse getCommentById(int commentId);
    List<CommentResponse> getCommentsByMovie(int movieId);
    CommentResponse updateComment(int commentId, String newContent, int rate);
    void deleteComment(int commentId);
    CommentResponse replyToComment(int userId, int parentCommentId, CommentRequest commentRequest); // Thêm phương thức mới
}
