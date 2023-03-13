package com.facedynamics.notifications.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CommentCreated extends NotificationContent {

    @NotEmpty
    private final String postText;
    @NotEmpty
    private final String commentText;

    public CommentCreated(String postText, String commentText) {
        super(Type.COMMENT_CREATED);
        this.postText = postText;
        this.commentText = commentText;
    }
}
