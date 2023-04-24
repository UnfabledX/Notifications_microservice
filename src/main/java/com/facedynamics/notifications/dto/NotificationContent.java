package com.facedynamics.notifications.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @Type(value = UserRegistered.class, name = "USER_REGISTERED"),
        @Type(value = PostCommented.class, name = "POST_COMMENTED"),
        @Type(value = CommentReplied.class, name = "COMMENT_REPLIED"),
        @Type(value = FollowedBy.class, name = "FOLLOWED_BY"),
})
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public abstract class NotificationContent {

    protected final Type type;
    protected final LocalDateTime entityCreatedAt;

    public enum Type {
        /**
         * A notification for a user who just registered
         * and needs to confirm his registration by email.
         */
        USER_REGISTERED,

        /**
         * A notification for owner-user who just
         * received a comment under his post.
         */
        POST_COMMENTED,

        /**
         * A notification for owner-user who just
         * received a reply under his comment.
         */
        COMMENT_REPLIED,

        /**
         * A notification for trigger-user who
         * just starts following another user.
         */
        FOLLOWING,

        /**
         * c is
         * followed by another user.
         */
        FOLLOWED_BY,
    }
}
