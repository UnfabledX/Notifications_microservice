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
public class PostLiked extends NotificationContent<PostLiked> {

    @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1)
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
