package com.facedynamics.notifications.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public record NotificationDto(

        @NotNull(message = "The recipient id must be present.")
        @Min(value = 1, message = "The value must be greater than or equal to 1")
        Long recipientId,

        @Min(value = 1, message = "The value must be greater than or equal to 1")
        Long createdById,

        @Valid
        NotificationContent content
) {
        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                NotificationDto that = (NotificationDto) o;
                return recipientId.equals(that.recipientId) &&
                        Objects.equals(createdById, that.createdById) &&
                        content.equals(that.content);
        }

        @Override
        public int hashCode() {
                return Objects.hash(recipientId, createdById, content);
        }
}
