package com.facedynamics.notifications.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class PasswordResetRequest extends NotificationContent {

    @Size(min = 3, max = 24, message = "Username mustn't be bigger then 24 letters and less then 3 letters")
    private final String username;
    @Email(message = "Email is written in a wrong format")
    private final String email;
    @NotEmpty
    private final String confirmationLink;
    private final int timeToLive;

    public PasswordResetRequest(String username, String email, String confirmationLink, int timeToLive) {
        super(Type.USER_PASSWORD_RESET_REQUEST);
        this.username = username;
        this.email = email;
        this.confirmationLink = confirmationLink;
        this.timeToLive = timeToLive;
    }
}
