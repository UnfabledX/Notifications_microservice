package com.facedynamics.notifications.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true)
public class FollowedBy extends NotificationContent<FollowedBy> implements ContentWithEmail{

    @Size(min = 3, max = 24, message = "Username mustn't be bigger then 24 letters and less then 3 letters")
    private final String recipientName;

    @Size(min = 3, max = 24, message = "Username mustn't be bigger then 24 letters and less then 3 letters")
    private final String createdByName;

    @NotEmpty(message = "Email address must not be empty")
    @Email(message = "Email is written in a wrong format")
    private final String email;

    public FollowedBy(String recipientName, String createdByName, String email, LocalDateTime entityCreatedAt) {
        super(Type.FOLLOWS, entityCreatedAt);
        this.recipientName = recipientName;
        this.createdByName = createdByName;
        this.email = email;
    }

}
