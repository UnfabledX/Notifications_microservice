package com.facedynamics.notifications.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;
import java.util.Objects;

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
        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                NotificationDto that = (NotificationDto) o;
                return recipientId.equals(that.recipientId) && createdById.equals(that.createdById)
                        && content.equals(that.content) && createdAt.equals(that.createdAt)
                        && Objects.equals(updatedAt, that.updatedAt);
        }

        @Override
        public int hashCode() {
                return Objects.hash(recipientId, createdById, content, createdAt, updatedAt);
        }
}
