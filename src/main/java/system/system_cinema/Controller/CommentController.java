package system.system_cinema.Controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.CommentEditRequest;
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

    @GetMapping("/get")
    public ApiResponse<List<CommentResponse>> getCommentsByMovie(
            @RequestParam @NotNull int movieId,
            @RequestParam(defaultValue = "0") @NotNull int page,
            @RequestParam(defaultValue = "5") @NotNull int size) {
        return ApiResponse.<List<CommentResponse>>builder()
                .message("Successful")
                .data(commentService.getCommentsByMovie(movieId, size, page))
                .build();
    }

    @GetMapping("/get-reply")
    public ApiResponse<List<CommentResponse>> getCommentsReply(
            @RequestParam @NotNull int commentId,
            @RequestParam(defaultValue = "0") @NotNull int page,
            @RequestParam(defaultValue = "5") @NotNull int size) {
        return ApiResponse.<List<CommentResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Successful")
                .data(commentService.getRepliesByParentId(commentId, size, page))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<CommentResponse> addComment(@Valid @RequestBody CommentRequest commentRequest) {
        return ApiResponse.<CommentResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Comment added successfully")
                .data(commentService.addComment(commentRequest))
                .build();
    }

    @PatchMapping("/update")
    public ApiResponse<CommentResponse> updateComment(@Valid @RequestBody CommentEditRequest request) {
        return ApiResponse.<CommentResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Comment updated successfully")
                .data(commentService.updateComment(request))
                .build();
    }

    @DeleteMapping("/delete")
    public ApiResponse<?> deleteComment(@RequestParam @NotNull int commentId) {
        commentService.deleteComment(commentId);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Comment deleted successfully")
                .build();
    }
}
