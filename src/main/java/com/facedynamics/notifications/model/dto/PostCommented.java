package com.facedynamics.notifications.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.facedynamics.notifications.utils.Constants.GREATER_THAN_OR_EQUAL_TO_1;
import static com.facedynamics.notifications.utils.Constants.THE_COMMENT_ID_MUST_BE_PRESENT;
import static com.facedynamics.notifications.utils.Constants.THE_COMMENT_TEXT_MUST_BE_PRESENT;

@Getter
@EqualsAndHashCode(callSuper = true)
public class PostCommented extends NotificationContent<PostCommented> {

    @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1)
    @NotNull(message = "The post id must be present.")
    private final Long postId;

    @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1)
    @NotNull(message = THE_COMMENT_ID_MUST_BE_PRESENT)
    private final Long commentId;

    @NotEmpty(message = "The post text must be present.")
    private final String postText;

    @NotEmpty(message = THE_COMMENT_TEXT_MUST_BE_PRESENT)
    private final String commentText;

    public PostCommented(Long postId, Long commentId, String postText,
                         String commentText, LocalDateTime entityCreatedAt) {
        super(Type.POST_COMMENTED, entityCreatedAt);
        this.postId = postId;
        this.commentId = commentId;
        this.postText = postText;
        this.commentText = commentText;
    }

    @JsonIgnore
    @Override
    public PostCommented getChild() {
        return this;
    }
}
