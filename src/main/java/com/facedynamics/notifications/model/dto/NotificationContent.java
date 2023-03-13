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
        @Type(value = CommentCreated.class, name = "COMMENT_CREATED")
        @Type(value = CommentDeleted.class, name = "COMMENT_DELETED")
})
@RequiredArgsConstructor
public abstract class NotificationContent {

    protected final Type type;

    enum Type {
        USER_REGISTERED,
        PASSWORD_RESET,
        COMMENT_CREATED,
        COMMENT_DELETED,
        REPLY_TO_COMMENT_CREATED,
        REPLY_TO_COMMENT_DELETED,
        FOLLOW_CREATED_BY, // notification for owner id
        FOLLOW_CREATED_FOR, //notification for trigger id
        FOLLOW_DELETED_BY,
        FOLLOW_DELETED_FOR,
        SUBSCRIPTION_CREATED_BY, // notification for owner id
        SUBSCRIPTION_CREATED_FOR, //notification for trigger id
        SUBSCRIPTION_UPDATED,
        SUBSCRIPTION_DELETED
    }
}
