package com.facedynamics.notifications.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true)
public class PostLiked extends NotificationContent {

    @Min(value = 1, message = "The value must be greater than or equal to 1")
    @NotNull(message = "The post id must be present.")
    private final Long postId;

    @NotEmpty(message = "The post text must be present.")
    private final String postText;

    public PostLiked(Long postId, String postText, LocalDateTime entityCreatedAt) {
        super(Type.POST_LIKED, entityCreatedAt);
        this.postId = postId;
        this.postText = postText;
    }
}
