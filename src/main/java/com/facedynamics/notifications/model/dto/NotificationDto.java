package com.facedynamics.notifications.model.dto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Map;


public record NotificationDto(

        Long createdById,
        Long recipientId,
        NotificationContent content,
        Map<String, Object> metadata,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
