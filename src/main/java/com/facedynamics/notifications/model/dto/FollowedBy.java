package com.facedynamics.notifications.model.dto;

import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class FollowedBy extends NotificationContent<FollowedBy> {

    @Size(min = 3, max = 24, message = "Username mustn't be bigger then 24 letters and less then 3 letters")
    private final String username;

    public FollowedBy(String username) {
        super(Type.FOLLOWING);
        this.username = username;
    }
}
