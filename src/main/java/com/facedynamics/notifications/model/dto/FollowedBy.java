package com.facedynamics.notifications.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true)
public class FollowedBy extends NotificationContent<FollowedBy> {

    @Size(min = 3, max = 24, message = "Username mustn't be bigger then 24 letters and less then 3 letters")
    private final String username;

    public FollowedBy(String username, LocalDateTime entityCreatedAt) {
        super(Type.FOLLOWING, entityCreatedAt);
        this.username = username;
    }

    @JsonIgnore
    @Override
    public FollowedBy getChild() {
        return this;
    }
}
