package com.facedynamics.notifications.services;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    List<Notification> getAllNotificationsByUserId(int page, int ownerId);

    void deleteAllNotificationsByOwnerId(int ownerId);

    Long deleteNotificationById(long notificationId);

    Notification createNotification(NotificationDto notification);

}
