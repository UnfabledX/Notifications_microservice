package com.facedynamics.notifications.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class SubscribedBy extends NotificationContent {

    @Size(min = 3, max = 24, message = "Username mustn't be bigger then 24 letters and less then 3 letters")
    private final String username;
    @NotEmpty
    private final String subscriptionType;

    public SubscribedBy(String username, String subscriptionType) {
        super(Type.SUBSCRIBED_BY);
        this.username = username;
        this.subscriptionType = subscriptionType;
    }
}
