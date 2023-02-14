package com.facedynamics.notifications.services;

import com.facedynamics.notifications.model.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> getAllNotificationsByUserId(int page, int ownerId);

    void deleteAllNotificationsByOwnerId(int ownerId);

    Long deleteNotificationById(long notificationId);
}
