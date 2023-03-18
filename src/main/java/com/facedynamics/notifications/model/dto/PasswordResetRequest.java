package com.facedynamics.notifications.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.facedynamics.notifications.utils.Constants.NO_BIGGER_THEN_24_AND_NO_LESS_THEN_3_LETTERS;
import static com.facedynamics.notifications.utils.Constants.LETTER_WRONG_FORMAT;

@Getter
@EqualsAndHashCode(callSuper = true)
public class PasswordResetRequest extends NotificationContent implements ContentWithEmail{

    @NotEmpty(message = "The name must be present")
    @Size(min = 3, max = 24, message = NO_BIGGER_THEN_24_AND_NO_LESS_THEN_3_LETTERS)
    private final String ownerName;

    @NotEmpty(message = "Email address must not be empty")
    @Email(message = LETTER_WRONG_FORMAT)
    private final String email;

    @NotEmpty(message = "Confirmation link must be provided")
    private final String confirmationLink;

    @NotNull(message = "Time of link to be active must be present")
    private final Integer timeToLive;

    public PasswordResetRequest(String ownerName, String email, String confirmationLink, Integer timeToLive) {
        super(Type.USER_PASSWORD_RESET_REQUEST);
        this.ownerName = ownerName;
        this.email = email;
        this.confirmationLink = confirmationLink;
        this.timeToLive = timeToLive;
    }
}
