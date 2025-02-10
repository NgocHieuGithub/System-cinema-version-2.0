package system.system_cinema.Service;

import system.system_cinema.DTO.Request.CommentEditRequest;
import system.system_cinema.DTO.Request.CommentRequest;
import system.system_cinema.DTO.Response.CommentResponse;
import java.util.List;

public interface ICommentService {
    List<CommentResponse> getRepliesByParentId(int parentId, int page, int size);

    CommentResponse addComment(CommentRequest commentRequest);
    List<CommentResponse> getCommentsByMovie(int movieId, int size, int page);
    CommentResponse updateComment(CommentEditRequest request);
    void deleteComment(int commentId);
}
