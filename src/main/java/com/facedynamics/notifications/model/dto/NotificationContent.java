package com.facedynamics.notifications.model.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.RequiredArgsConstructor;

// todo: https://www.baeldung.com/jackson-inheritance
// todo: https://thorben-janssen.com/persist-postgresqls-jsonb-data-type-hibernate/
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @Type(value = UserRegistered.class, name = "USER_REGISTERED"),
        @Type(value = PasswordReset.class, name = "PASSWORD_RESET"),
        @Type(value = CommentCreated.class, name = "COMMENT_CREATED"),
        @Type(value = CommentReplied.class, name = "COMMENT_REPLIED"),
        @Type(value = FollowedBy.class, name = "FOLLOWED_BY"),
        @Type(value = SubscribedBy.class, name = "SUBSCRIBED_BY"),
})
@RequiredArgsConstructor
public abstract class NotificationContent {

    protected final Type type;

    public Type getType() {
        return type;
    }

    public enum Type {
        /**
         * A notification for a user who just registered
         * and needs to confirm his registration by email.
         */
        USER_REGISTERED,

        /**
         * A notification for a user who wants to reset
         * his password.
         */
        PASSWORD_RESET,

        /**
         * A notification for owner-user who just
         * received a comment under his post.
         */
        COMMENT_CREATED,

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

        /**
         * A notification for trigger-user who is
         * subscribing for other user.
         */
        SUBSCRIBING,

        /**
         * A notification for owner-user who is
         * subscribed by another user.
         */
        SUBSCRIBED_BY,

        /**
         * A notification for owner-user who just
         * made a subscription update
         */
        SUBSCRIPTION_UPDATE,

        /**
         * Notifications for all users who had the specific
         * subscription, but it was changed by owner-user.
         */
        SUBSCRIPTION_UPDATED_FOR,
    }
}
