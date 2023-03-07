package com.facedynamics.notifications.services;

import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.model.dto.NotificationUserServiceDTO;

public interface EmailService {

    void sendEmail(NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO,
                   String triggerUserName);
}
