package com.facedynamics.notifications.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true)
public class UserRegistered extends NotificationContent{

    public static final String NO_BIGGER_THEN_24_AND_NO_LESS_THEN_3_LETTERS =
            "Username mustn't be bigger then 24 letters and less then 3 letters";

    @NotEmpty(message = "The name must be present")
    @Size(min = 3, max = 24, message = NO_BIGGER_THEN_24_AND_NO_LESS_THEN_3_LETTERS)
    private final String ownerName;

    @NotEmpty(message = "Email address must not be empty")
    @Email(message = "Email is written in a wrong format")
    private final String email;

    @NotEmpty(message = "Confirmation link must be provided")
    private final String confirmationLink;

    @NotNull(message = "Time of link to be active must be present")
    private final Integer timeToLive;

    public UserRegistered(String ownerName, String email, String confirmationLink,
                          int timeToLive, LocalDateTime entityCreatedAt) {
        super(Type.USER_REGISTERED, entityCreatedAt);
        this.ownerName = ownerName;
        this.email = email;
        this.confirmationLink = confirmationLink;
        this.timeToLive = timeToLive;
    }

}
