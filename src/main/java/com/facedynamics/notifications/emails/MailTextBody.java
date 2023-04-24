package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.dto.NotificationUserServiceDTO;

import java.io.StringWriter;

public interface MailTextBody {

    StringWriter getLetterBody(NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO,
                               Object payload);

    String getLetterSubject();
}
