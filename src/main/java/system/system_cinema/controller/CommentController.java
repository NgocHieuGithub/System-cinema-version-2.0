package system.system_cinema.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.dto.ApiResponse;
import system.system_cinema.dto.request.CommentEditRequest;
import system.system_cinema.dto.request.CommentRequest;
import system.system_cinema.dto.response.CommentResponse;
import system.system_cinema.service.ICommentService;
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
                .data(commentService.getRepliesByParentId(commentId, page, size))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<CommentResponse> addComment(@Valid @RequestBody CommentRequest commentRequest) {
        commentService.addComment(commentRequest);
        return ApiResponse.<CommentResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Comment added successfully")
                .build();
    }

    @PatchMapping("/update")
    public ApiResponse<CommentResponse> updateComment(@Valid @RequestBody CommentEditRequest request) {
        commentService.updateComment(request);
        return ApiResponse.<CommentResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Comment updated successfully")
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
