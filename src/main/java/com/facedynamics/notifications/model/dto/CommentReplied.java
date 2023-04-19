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
public class CommentReplied extends NotificationContent<CommentReplied> {

    @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1)
    @NotNull(message = THE_COMMENT_ID_MUST_BE_PRESENT)
    private final Long commentId;

    @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1)
    @NotNull(message = "The reply id must be present.")
    private final Long replyId;

    @NotEmpty(message = THE_COMMENT_TEXT_MUST_BE_PRESENT)
    private final String commentText;

    @NotEmpty(message = "The reply text must be present.")
    private final String replyText;

    public CommentReplied(Long commentId, Long replyId, String commentText, String replyText, LocalDateTime entityCreatedAt) {
        super(Type.COMMENT_REPLIED, entityCreatedAt);
        this.commentId = commentId;
        this.replyId = replyId;
        this.commentText = commentText;
        this.replyText = replyText;
    }

    @JsonIgnore
    @Override
    public CommentReplied getChild() {
        return this;
    }
}
