package system.system_cinema.service;

import system.system_cinema.dto.request.CommentEditRequest;
import system.system_cinema.dto.request.CommentRequest;
import system.system_cinema.dto.response.CommentResponse;
import java.util.List;

public interface ICommentService {
    List<CommentResponse> getRepliesByParentId(int parentId, int page, int size);

    CommentResponse addComment(CommentRequest commentRequest);
    List<CommentResponse> getCommentsByMovie(int movieId, int size, int page);
    CommentResponse updateComment(CommentEditRequest request);
    void deleteComment(int commentId);
}
