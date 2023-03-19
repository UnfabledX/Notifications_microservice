package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.model.NotificationResponseDTO;
import com.facedynamics.notifications.model.dto.NotificationDto;

public interface Executable {
    NotificationResponseDTO execute(NotificationDto dto);
}
