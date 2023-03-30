package com.facedynamics.notifications.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.facedynamics.notifications.utils.Constants.*;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CommentDisliked extends NotificationContent<CommentDisliked> {

    @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1)
    @NotNull(message = THE_COMMENT_ID_MUST_BE_PRESENT)
    private final Long commentId;

    @NotEmpty(message = THE_COMMENT_TEXT_MUST_BE_PRESENT)
    private final String commentText;

    public CommentDisliked(Long commentId, String commentText, LocalDateTime entityCreatedAt) {
        super(Type.COMMENT_DISLIKED, entityCreatedAt);
        this.commentId = commentId;
        this.commentText = commentText;
    }
}
