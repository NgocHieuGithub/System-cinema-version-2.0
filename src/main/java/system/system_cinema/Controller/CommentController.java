package system.system_cinema.Controller;

import jakarta.validation.Valid;
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
    ICommentService commentService;

    @PostMapping("/add")
    public ApiResponse<CommentResponse> addComment(@Valid @RequestBody CommentRequest commentRequest) {
        try {
            return ApiResponse.<CommentResponse>builder()
                    .message("Comment added successfully")
                    .data(commentService.addComment(commentRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<CommentResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/get")
    public ApiResponse<List<CommentResponse>> getCommentsByMovie(@RequestParam int movieId) {
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

    @PutMapping("/update")
    public ApiResponse<CommentResponse> updateComment(@RequestBody CommentRequest request) {
        try {
            return ApiResponse.<CommentResponse>builder()
                    .message("Comment updated successfully")
                    .data(commentService.updateComment(request))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<CommentResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/delete")
    public ApiResponse<?> deleteComment(@RequestParam int commentId) {
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
