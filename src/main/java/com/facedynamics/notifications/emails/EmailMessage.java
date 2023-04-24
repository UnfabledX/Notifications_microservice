package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.NotificationDto;
import lombok.Setter;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

@Setter
@Component
public abstract class EmailMessage implements MailTextBody{

    protected VelocityEngine engine;
    protected NotificationDto receivedDTO;
    protected NotificationUserServiceDTO ownerDTO;
    protected Object payload;

}
