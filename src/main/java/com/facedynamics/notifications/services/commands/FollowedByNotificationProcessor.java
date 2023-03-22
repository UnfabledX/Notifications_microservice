package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.model.NotificationResponseDTO;
import com.facedynamics.notifications.model.dto.NotificationDto;
import org.springframework.stereotype.Component;

@Component("FOLLOWED_BY")
public class FollowedByNotificationProcessor extends AbstractNotificationProcessor {
    @Override
    public NotificationResponseDTO process(NotificationDto dto) {
        return null;
    }
}
