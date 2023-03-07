package com.facedynamics.notifications.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CommentCreated extends NotificationContent {

    private final Long commentId;
    private final Long postId;

    public CommentCreated(Long commentId, Long postId) {
        super(Type.COMMENT_CREATED);
        this.commentId = commentId;
        this.postId = postId;
    }

    @Override
    String emailTitle() {
        return "Someone commented your post";
    }
}
