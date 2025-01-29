package system.system_cinema.Mapper;

import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Request.CommentRequest;
import system.system_cinema.DTO.Response.CommentResponse;
import system.system_cinema.Model.Comment;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public CommentResponse toCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .userId(comment.getUser().getId())
                .movieId(comment.getMovie().getId())
                .username(comment.getUser().getUsername())
                .content(comment.getContent())
                .rate(comment.getRate())  // Bao gồm trường đánh giá
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .replies(comment.getReplies().stream()
                        .map(this::toCommentResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    public Comment toComment(CommentRequest commentRequest, Comment parentComment) {
        return Comment.builder()
                .content(commentRequest.getContent())
                .rate(commentRequest.getRate())  // Bao gồm trường đánh giá
                .parentComment(parentComment)
                .username(commentRequest.getUsername())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
