package com.facedynamics.notifications.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

import static com.facedynamics.notifications.utils.Constants.GREATER_THAN_OR_EQUAL_TO_1;

public record NotificationDto(

        @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1)
        Long recipientId,

        @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1)
        Long createdById,

        @Valid
        NotificationContent content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
