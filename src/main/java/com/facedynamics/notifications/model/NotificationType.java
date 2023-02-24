package com.facedynamics.notifications.model;

public enum NotificationType {
    REGISTRATION    (1, "registration"),
    RESET_PASSWORD  (2, "password reset"),
    COMMENT         (3, "comment"),
    REPLY           (4, "reply"),
    FOLLOW          (5, "follow"),
    SUBSCRIPTION    (6, "subscription");

    private int id;
    private String name;

    NotificationType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static NotificationType getType(String name) {

        return NotificationType.valueOf(name.toUpperCase().trim());
    }
}
