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
})
@RequiredArgsConstructor
public abstract class NotificationContent {

    protected final Type type;

    abstract String emailTitle();

    enum Type {
        COMMENT_CREATED,
        COMMENT_DELETED
    }
}
