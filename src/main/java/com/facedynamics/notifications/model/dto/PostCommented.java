package com.facedynamics.notifications.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class PostCommented extends NotificationContent {

    @Min(1)
    private final Long postId;
    @Min(1)
    private final Long commentId;
    @NotEmpty
    private final String postText;
    @NotEmpty
    private final String commentText;

    public PostCommented(Long postId, Long commentId, String postText, String commentText) {
        super(Type.POST_COMMENTED);
        this.postId = postId;
        this.commentId = commentId;
        this.postText = postText;
        this.commentText = commentText;
    }
}
