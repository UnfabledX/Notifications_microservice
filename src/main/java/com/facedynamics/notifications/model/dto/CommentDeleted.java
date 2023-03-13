package com.facedynamics.notifications.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CommentDeleted extends NotificationContent{

    private final Long commentId;
    private final Long postId;

    public CommentDeleted(Long commentId, Long postId) {
        super(NotificationContent.Type.COMMENT_CREATED);
        this.commentId = commentId;
        this.postId = postId;
    }
}
