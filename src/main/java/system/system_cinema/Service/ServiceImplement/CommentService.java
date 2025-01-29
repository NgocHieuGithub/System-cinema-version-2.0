package system.system_cinema.Service.ServiceImplement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.CommentRequest;
import system.system_cinema.DTO.Response.CommentResponse;
import system.system_cinema.Mapper.CommentMapper;
import system.system_cinema.Model.Comment;
import system.system_cinema.Model.Movie;
import system.system_cinema.Model.User;
import system.system_cinema.Repository.CommentRepository;
import system.system_cinema.Repository.MovieRepository;
import system.system_cinema.Repository.UserRepository;
import system.system_cinema.Service.ICommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService implements ICommentService {

    CommentRepository commentRepository;
    MovieRepository movieRepository;
    UserRepository userRepository;
    CommentMapper commentMapper;

    @Override
    public CommentResponse addComment(int userId, CommentRequest commentRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Movie movie = movieRepository.findById(commentRequest.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Comment parentComment = null;
        if (commentRequest.getParentCommentId() != 0) {
            parentComment = commentRepository.findById(commentRequest.getParentCommentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
        }

        Comment comment = commentMapper.toComment(commentRequest, parentComment);
        comment.setUser(user);
        comment.setMovie(movie);
        comment.setRate(commentRequest.getRate());

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toCommentResponse(savedComment);
    }

    @Override
    public CommentResponse getCommentById(int commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public List<CommentResponse> getCommentsByMovie(int movieId) {
        List<Comment> comments = commentRepository.findAll().stream()
                .filter(comment -> comment.getMovie().getId() == movieId && comment.getParentComment() == null)
                .toList();
        return comments.stream().map(commentMapper::toCommentResponse).collect(Collectors.toList());
    }

    @Override
    public CommentResponse updateComment(int commentId, String newContent, int rate) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(newContent);
        comment.setRate(rate);

        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toCommentResponse(updatedComment);
    }
    @Override
    public CommentResponse replyToComment(int userId, int parentCommentId, CommentRequest commentRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));

        Comment replyComment = commentMapper.toComment(commentRequest, parentComment);
        replyComment.setUser(user);
        replyComment.setMovie(parentComment.getMovie());

        Comment savedReplyComment = commentRepository.save(replyComment);
        return commentMapper.toCommentResponse(savedReplyComment);
    }

    @Override
    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }
}
