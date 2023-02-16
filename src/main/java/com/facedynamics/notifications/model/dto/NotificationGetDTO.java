package com.facedynamics.notifications.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class NotificationGetDTO {

    @NotEmpty
    private int ownerId;
    @NotEmpty
    private String notificationType;
    @NotEmpty
    private NotificationDetails details;
}
