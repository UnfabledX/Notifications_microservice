package com.facedynamics.notifications.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class NotificationGetDTO {

    @Min(1)
    private int ownerId;

    @NotEmpty
    private String notificationType;

    @Valid
    @NotEmpty
    private NotificationDetails details;
}
