package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.model.dto.NotificationDto;

public interface NotificationProcessor {
    NotificationDto process(NotificationDto dto);
}
