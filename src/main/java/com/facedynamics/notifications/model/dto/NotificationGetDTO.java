package com.facedynamics.notifications.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class NotificationGetDTO {

    @NotEmpty
    @Positive
    private int ownerId;

    @NotEmpty
    private String notificationType;

    @Valid
    @NotEmpty
    private NotificationDetails details;
}
