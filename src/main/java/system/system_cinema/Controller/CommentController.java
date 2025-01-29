package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.CommentRequest;
import system.system_cinema.DTO.Response.CommentResponse;
import system.system_cinema.Service.ICommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {
    final ICommentService commentService;
    @PostMapping("/add/{userId}")
    public ApiResponse<CommentResponse> addComment(@PathVariable int userId, @RequestBody CommentRequest commentRequest) {
        try {
            return ApiResponse.<CommentResponse>builder()
                    .message("Comment added successfully")
                    .data(commentService.addComment(userId, commentRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<CommentResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/get/{commentId}")
    public ApiResponse<CommentResponse> getCommentById(@PathVariable int commentId) {
        try {
            return ApiResponse.<CommentResponse>builder()
                    .message("Successful")
                    .data(commentService.getCommentById(commentId))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<CommentResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/movie/{movieId}")
    public ApiResponse<List<CommentResponse>> getCommentsByMovie(@PathVariable int movieId) {
        try {
            return ApiResponse.<List<CommentResponse>>builder()
                    .message("Successful")
                    .data(commentService.getCommentsByMovie(movieId))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<CommentResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PutMapping("/update/{commentId}")
    public ApiResponse<CommentResponse> updateComment(@PathVariable int commentId, @RequestParam String newContent, @RequestParam int rate) {
        try {
            return ApiResponse.<CommentResponse>builder()
                    .message("Comment updated successfully")
                    .data(commentService.updateComment(commentId, newContent, rate))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<CommentResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/reply/{userId}/{parentCommentId}")
    public ApiResponse<CommentResponse> replyToComment(
            @PathVariable int userId,
            @PathVariable int parentCommentId,
            @RequestBody CommentRequest commentRequest) {
        try {
            CommentResponse replyResponse = commentService.replyToComment(userId, parentCommentId, commentRequest);
            return ApiResponse.<CommentResponse>builder()
                    .message("Reply added successfully")
                    .data(replyResponse)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<CommentResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/delete/{commentId}")
    public ApiResponse<?> deleteComment(@PathVariable int commentId) {
        try {
            commentService.deleteComment(commentId);
            return ApiResponse.builder()
                    .message("Comment deleted successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
