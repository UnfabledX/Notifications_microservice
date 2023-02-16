package com.facedynamics.notifications.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class NotificationGetDTO {

    @NotEmpty
    private int ownerId;
    @NotEmpty
    private String notificationType;
    @NotEmpty
    private NotificationDetails details;
}
