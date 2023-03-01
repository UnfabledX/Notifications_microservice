package com.facedynamics.notifications.model;

import com.facedynamics.notifications.handler.WrongEnumException;
import lombok.Getter;

@Getter
public enum NotificationType {
    REGISTRATION    (1, "registration"),
    RESET_PASSWORD  (2, "password reset"),
    COMMENT         (3, "comment"),
    REPLY           (4, "reply"),
    FOLLOW          (5, "follow"),
    SUBSCRIPTION    (6, "subscription");

    private final int id;
    private final String name;

    NotificationType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static NotificationType getType(String name) {
        try {
            return NotificationType.valueOf(name.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new WrongEnumException(e.getMessage(), name);
        }
    }
}
