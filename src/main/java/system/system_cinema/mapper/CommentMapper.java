package system.system_cinema.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.springframework.stereotype.Component;
import system.system_cinema.dto.response.CommentResponse;
import system.system_cinema.domain.Comment;

import java.util.List;

@Component
@Mapper(componentModel = "spring", mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG)
public interface CommentMapper {

    @Mapping(source = "user.username", target = "username")
    @Mapping(target = "replyCount", expression = "java(comment.getReplies() != null ? comment.getReplies().size() : 0)")
    CommentResponse toCommentResponse(Comment comment);

    List<CommentResponse> toCommentResponseList(List<Comment> comments);
}
