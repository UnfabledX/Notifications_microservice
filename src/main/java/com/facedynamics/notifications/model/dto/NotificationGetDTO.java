package com.facedynamics.notifications.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.facedynamics.notifications.utils.Constants.GREATER_THAN_OR_EQUAL_TO_1;

@Data
@AllArgsConstructor
public class NotificationGetDTO {

    @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1)
    private int ownerId;

    @NotEmpty(message = "Notification type is mandatory")
    private String notificationType;

    @Valid
    @NotNull(message = "Details of notifications should be present")
    private NotificationDetails details;
}
