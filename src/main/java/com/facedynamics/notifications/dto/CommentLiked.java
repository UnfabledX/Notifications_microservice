package com.facedynamics.notifications.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CommentLiked extends NotificationContent {

    @Min(value = 1, message = "The value must be greater than or equal to 1")
    @NotNull(message = "The comment id must be present.")
    private final Long commentId;

    @NotEmpty(message = "The comment text must be present.")
    private final String commentText;

    public CommentLiked(Long commentId, String commentText, LocalDateTime entityCreatedAt) {
        super(Type.COMMENT_LIKED, entityCreatedAt);
        this.commentId = commentId;
        this.commentText = commentText;
    }
}