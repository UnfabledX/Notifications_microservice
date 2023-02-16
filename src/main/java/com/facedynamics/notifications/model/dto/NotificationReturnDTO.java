package com.facedynamics.notifications.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationReturnDTO {

    private String triggererName;

    private String textBody;

    private LocalDateTime createdAt;
}
