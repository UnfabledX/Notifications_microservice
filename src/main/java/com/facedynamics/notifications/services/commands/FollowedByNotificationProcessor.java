package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.model.dto.NotificationDto;
import org.springframework.stereotype.Component;

@Component
public class FollowedByNotificationProcessor implements NotificationProcessor {
    @Override
    public NotificationDto process(NotificationDto dto) {
        return null;
    }
}
