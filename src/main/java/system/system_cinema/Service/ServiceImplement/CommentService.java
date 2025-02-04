package system.system_cinema.Service.ServiceImplement;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.ComboRequest;
import system.system_cinema.DTO.Request.CommentRequest;
import system.system_cinema.DTO.Response.ComboResponse;
import system.system_cinema.DTO.Response.CommentResponse;
import system.system_cinema.Mapper.CommentMapper;
import system.system_cinema.Model.Comment;
import system.system_cinema.Model.Movie;
import system.system_cinema.Model.User;
import system.system_cinema.Repository.CommentRepository;
import system.system_cinema.Repository.MovieRepository;
import system.system_cinema.Repository.UserRepository;
import system.system_cinema.Service.ICommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    public CommentResponse addComment(CommentRequest commentRequest) {
        if (commentRequest.getUser_id() != getIdUser(SecurityContextHolder.getContext().getAuthentication().getName())){
            throw new RuntimeException("Not authorized to add comment");
        }
        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .user(userRepository.findById(commentRequest.getUser_id()).orElseThrow(() -> new EntityNotFoundException("Not found User")))
                .movie(movieRepository.findById(commentRequest.getMovieId()).orElseThrow(() -> new EntityNotFoundException("Not found Product")))
                .dateCreate(LocalDateTime.now())
                .build();
        if (commentRequest.getParentCommentId() != 0 ){
            if (commentRepository.findById(commentRequest.getParentCommentId()).orElseThrow(() -> new EntityNotFoundException("not found cmt")).getMovie().getId() != commentRequest.getMovieId()){
                throw new EntityNotFoundException("Rely comment must be in the same product" );
            }
            comment.setParentComment(commentRepository.findById(commentRequest.getParentCommentId()).orElseThrow(() -> new EntityNotFoundException("Not found Comment")));
        }
        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }


    @Override
    public List<CommentResponse> getCommentsByMovie(int movieId) {
        List<Comment> comments = commentRepository.findAll().stream()
                .filter(comment -> comment.getMovie().getId() == movieId && comment.getParentComment() == null)
                .toList();
        return comments.stream().map(commentMapper::toCommentResponse).collect(Collectors.toList());
    }

    @Override
    public CommentResponse updateComment(CommentRequest request) {
        Comment comment = commentRepository.findById(request.getUser_id()).orElseThrow(() -> new EntityNotFoundException("Not found comment"));
        if (Objects.equals(comment.getUser().getUsername(), SecurityContextHolder.getContext().getAuthentication().getName())){
            comment.setContent(request.getContent());
            comment.setRate(request.getRate());
        } else{
            throw new RuntimeException("Not authorized to edit comment");
        };
        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }

    private int getIdUser(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Not found user")).getId();
    }
}
