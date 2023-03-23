package com.facedynamics.notifications.model.dto;

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
        @Type(value = PasswordResetRequest.class, name = "USER_PASSWORD_RESET_REQUEST"),
        @Type(value = PostCommented.class, name = "POST_COMMENTED"),
        @Type(value = CommentReplied.class, name = "COMMENT_REPLIED"),
        @Type(value = FollowedBy.class, name = "FOLLOWED_BY"),
})
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public abstract class NotificationContent<T extends NotificationContent<T>> {

    protected final Type type;

    protected LocalDateTime entityCreatedAt;

    public T getChild(){
        return (T) this;
    }

    public enum Type {
        /**
         * A notification for a user who just registered
         * and needs to confirm his registration by email.
         */
        USER_REGISTERED,

        /**
         * A notification for a user who just requested to
         * reset his password.
         */
        USER_PASSWORD_RESET_REQUEST,

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
         * A notification for owner-user who is
         * followed by another user.
         */
        FOLLOWED_BY,
    }
}
