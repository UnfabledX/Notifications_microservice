package com.facedynamics.notifications.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY,
        content = JsonInclude.Include.NON_NULL)
public record NotificationDto(
        Long recipientId,
        Long createdById,
        NotificationContent content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
