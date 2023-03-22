package com.facedynamics.notifications.model.dto;

/**
 * The marker interface which indicates that specific
 * NotificationContent classes already include a user email,
 * for example UserRegistered, PasswordResetRequest classes.
 */
public interface ContentWithEmail {
    String getEmail();
}
