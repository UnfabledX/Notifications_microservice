package com.facedynamics.notifications.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

import static com.facedynamics.notifications.utils.Constants.GREATER_THAN_OR_EQUAL_TO_1;
public record NotificationDto(

        @NotNull(message = "The recipient id must be present.")
        @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1)
        Long recipientId,

        @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1)
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
