package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.dto.NotificationUserServiceDTO;
import com.facedynamics.notifications.dto.UserRegistered;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
public class UserRegisteredEmailMessage extends EmailMessage {

    public static final String NEW_REGISTRATION = "Your registration for FaceDynamics social network!";

    private final String emailTemplate;

    public UserRegisteredEmailMessage(@Value("${source.mail.template.user-registered}") String emailTemplate,
                                      VelocityEngine engine) {
        super(engine);
        this.emailTemplate = emailTemplate;
    }

    @Override
    public StringWriter getLetterBody(NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO,
                                      Object payload) {
        if (receivedDTO.content() instanceof UserRegistered registered) {

            VelocityContext context = new VelocityContext();
            context.put("ownerName", registered.getOwnerName());
            context.put("email", registered.getEmail());
            context.put("link", registered.getConfirmationLink());
            context.put("timeToLive", registered.getTimeToLive());
            context.put("createdAt", registered.getEntityCreatedAt());
            Template template = engine.getTemplate(emailTemplate);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return writer;
        } else {
            throw new IllegalArgumentException("Wrong type of the notification!");
        }
    }

    @Override
    public String getLetterSubject() {
        return NEW_REGISTRATION;
    }
}
