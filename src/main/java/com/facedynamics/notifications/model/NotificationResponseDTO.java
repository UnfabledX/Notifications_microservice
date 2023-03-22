package com.facedynamics.notifications.model;


import com.facedynamics.notifications.model.dto.NotificationContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class NotificationResponseDTO {

    private String triggererName;

    private NotificationContent.Type type;

    private LocalDateTime createdAt;
}

