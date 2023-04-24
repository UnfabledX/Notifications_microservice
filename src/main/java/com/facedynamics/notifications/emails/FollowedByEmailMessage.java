package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.dto.NotificationUserServiceDTO;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
public class FollowedByEmailMessage extends EmailMessage {

    public static final String NEW_FOLLOW = "You have a NEW Follow!";

    public FollowedByEmailMessage(VelocityEngine engine) {
        super(engine);
    }

    @Override
    public StringWriter getLetterBody(NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO,
                                      Object payload) {
        //todo
        return null;
    }

    @Override
    public String getLetterSubject(){
        return NEW_FOLLOW;
    }
}
