package com.facedynamics.notifications.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CommentReplied extends NotificationContent {

    @Min(1)
    private final Long commentId;
    @Min(1)
    private final Long replyId;
    @NotEmpty
    private final String commentText;
    @NotEmpty
    private final String replyText;

    public CommentReplied(Long commentId, Long replyId, String commentText, String replyText) {
        super(Type.COMMENT_REPLIED);
        this.commentId = commentId;
        this.replyId = replyId;
        this.commentText = commentText;
        this.replyText = replyText;
    }
}
