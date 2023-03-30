package com.facedynamics.notifications.services;

import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.NotificationDto;

public interface EmailService {

    void sendEmail(NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO,
                   Object payload);
}
