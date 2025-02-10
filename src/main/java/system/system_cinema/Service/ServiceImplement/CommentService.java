package system.system_cinema.Service.ServiceImplement;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.CommentEditRequest;
import system.system_cinema.DTO.Request.CommentRequest;
import system.system_cinema.DTO.Response.CommentResponse;
import system.system_cinema.Mapper.CommentMapper;
import system.system_cinema.Model.Comment;
import system.system_cinema.Repository.CommentRepository;
import system.system_cinema.Repository.MovieRepository;
import system.system_cinema.Repository.UserRepository;
import system.system_cinema.Service.ICommentService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService implements ICommentService {

    CommentRepository commentRepository;
    MovieRepository movieRepository;
    UserRepository userRepository;
    CommentMapper commentMapper;


    @Override
    public List<CommentResponse> getCommentsByMovie(int movieId, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        return commentMapper.toCommentResponseList(commentRepository.findParentComments(pageable, movieId));
    }

    @Override
    public List<CommentResponse> getRepliesByParentId(int parentId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentMapper.toCommentResponseList(commentRepository.findRepliesByParentId(pageable,parentId));
    }

    @Transactional
    @Override
    public CommentResponse addComment(CommentRequest commentRequest) {
        int userId = getIdUser(SecurityContextHolder.getContext().getAuthentication().getName());
        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .user(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Not found User")))
                .movie(movieRepository.findById(commentRequest.getMovieId()).orElseThrow(() -> new EntityNotFoundException("Not found Product")))
                .dateCreate(LocalDateTime.now())
                .build();
        if (commentRequest.getParentCommentId() != 0 ){
            if (commentRepository.findById(commentRequest.getParentCommentId()).orElseThrow(() -> new EntityNotFoundException("not found cmt")).getMovie().getId() != commentRequest.getMovieId()){
                throw new EntityNotFoundException("Rely comment must be in the same movie" );
            }
            comment.setParentComment(commentRepository.findById(commentRequest.getParentCommentId()).orElseThrow(() -> new EntityNotFoundException("Not found Comment")));
        }
        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }



    @Transactional
    @Override
    public CommentResponse updateComment(CommentEditRequest request) {
        Comment comment = commentRepository.findById(request.getUser_id()).orElseThrow(() -> new EntityNotFoundException("Not found comment"));
        if (Objects.equals(comment.getUser().getUsername(), SecurityContextHolder.getContext().getAuthentication().getName())){
            comment.setContent(request.getContent());
        } else{
            throw new RuntimeException("Not authorized to edit comment");
        };
        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(int commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Not found comment"));
        if (Objects.equals(comment.getUser().getUsername(), SecurityContextHolder.getContext().getAuthentication().getName())){
            if (comment.getReplies() != null){
                commentRepository.makeRepliesIndependent(comment.getId());
            }
            commentRepository.delete(comment);
        } else{
            throw new RuntimeException("Not authorized");
        }
    }

    private int getIdUser(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Not found user")).getId();
    }
}
