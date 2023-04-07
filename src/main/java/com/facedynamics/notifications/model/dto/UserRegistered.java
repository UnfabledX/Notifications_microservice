package com.facedynamics.notifications.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.facedynamics.notifications.utils.Constants.NO_BIGGER_THEN_24_AND_NO_LESS_THEN_3_LETTERS;

@Getter
@EqualsAndHashCode(callSuper = true)
public class UserRegistered extends NotificationContent<UserRegistered> implements ContentWithEmail{

    @NotEmpty(message = "The name must be present")
    @Size(min = 3, max = 24, message = NO_BIGGER_THEN_24_AND_NO_LESS_THEN_3_LETTERS)
    private final String recipientName;

    @NotEmpty(message = "Email address must not be empty")
    @Email(message = "Email is written in a wrong format")
    private final String email;

    @NotEmpty(message = "Confirmation link must be provided")
    private final String confirmationLink;

    @NotNull(message = "Time of link to be active must be present")
    private final Integer timeToLive;

    public UserRegistered(String recipientName, String email, String confirmationLink,
                          int timeToLive, LocalDateTime entityCreatedAt) {
        super(Type.USER_REGISTERED, entityCreatedAt);
        this.recipientName = recipientName;
        this.email = email;
        this.confirmationLink = confirmationLink;
        this.timeToLive = timeToLive;
    }
}
