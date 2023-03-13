package com.facedynamics.notifications.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CommentReplied extends NotificationContent {

    @NotEmpty
    private final String commentText;
    @NotEmpty
    private final String replyText;

    public CommentReplied(String commentText, String replyText) {
        super(Type.COMMENT_REPLIED);
        this.commentText = commentText;
        this.replyText = replyText;
    }
}
