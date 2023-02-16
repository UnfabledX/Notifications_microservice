package com.facedynamics.notifications.model.dto;

import com.facedynamics.notifications.model.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificationReturnDTO {

    private String triggererName;

    private NotificationType type;

    private LocalDateTime createdAt;
}
