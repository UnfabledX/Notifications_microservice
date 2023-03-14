package com.facedynamics.notifications.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class PostCommented extends NotificationContent {

    @NotEmpty
    private final String postText;
    @NotEmpty
    private final String commentText;

    public PostCommented(String postText, String commentText) {
        super(Type.POST_COMMENTED);
        this.postText = postText;
        this.commentText = commentText;
    }
}
