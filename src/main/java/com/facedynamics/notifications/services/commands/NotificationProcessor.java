package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.dto.NotificationDto;

public interface NotificationProcessor {
    NotificationDto process(NotificationDto dto);
}
