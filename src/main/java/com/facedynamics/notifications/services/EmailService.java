package com.facedynamics.notifications.services;

import com.facedynamics.notifications.dto.NotificationUserServiceDTO;
import com.facedynamics.notifications.dto.NotificationDto;

public interface EmailService {

    void sendEmail(NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO,
                   Object payload);
}
