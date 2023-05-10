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
        @Type(value = PostLiked.class, name = "POST_LIKED"),
        @Type(value = PostDisliked.class, name = "POST_DISLIKED"),
        @Type(value = CommentLiked.class, name = "COMMENT_LIKED"),
        @Type(value = CommentDisliked.class, name = "COMMENT_DISLIKED"),
        @Type(value = FollowedBy.class, name = "FOLLOWS"),
        @Type(value = WaitingApproval.class, name = "WAITING_APPROVE"),
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
         * A notification for owner-user when another
         * user wants to follow him but his account
         * is private, so approval for the follow must
         *  be granted.
         */
        WAITING_APPROVE,

        /**
         * A notification for owner-user when another
         * user follows him.
         */
        FOLLOWS,

        /**
         * A notification for owner-user who created
         * the post and somebody liked it.
         */
        POST_LIKED,

        /**
         * A notification for owner-user who created
         * the post and somebody disliked it.
         */
        POST_DISLIKED,

        /**
         * A notification for owner-user who created
         * the comment and somebody liked it.
         */
        COMMENT_LIKED,

        /**
         * A notification for owner-user who created
         * the comment and somebody disliked it.
         */
        COMMENT_DISLIKED
    }
}
