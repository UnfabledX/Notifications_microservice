package com.facedynamics.notifications.model.dto;

import java.time.LocalDateTime;

public record NotificationDto(
        Long recipientId,
        Long createdById,
        NotificationContent content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
