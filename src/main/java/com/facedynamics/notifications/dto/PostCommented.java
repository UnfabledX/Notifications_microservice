package com.facedynamics.notifications.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true)
public class PostCommented extends NotificationContent {

    public static final String GREATER_THAN_OR_EQUAL_TO_1 = "The value must be greater than or equal to 1";
    public static final String THE_COMMENT_TEXT_MUST_BE_PRESENT = "The comment text must be present.";
    public static final String THE_COMMENT_ID_MUST_BE_PRESENT = "The comment id must be present.";

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

}
