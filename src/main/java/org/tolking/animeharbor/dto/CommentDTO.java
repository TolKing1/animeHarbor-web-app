package org.tolking.animeharbor.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.tolking.animeharbor.dto.user.UserCommentDTO;
import org.tolking.animeharbor.entities.Comment;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class CommentDTO extends DTOConverter<Comment, CommentDTO> {
    private UserCommentDTO user;
    private String comment;
    private LocalDateTime createdAt;

    @Override
    protected Class<Comment> getTypeEntity() {
        return Comment.class;
    }

    @Override
    protected Class<CommentDTO> getTypeDTO() {
        return CommentDTO.class;
    }
}
