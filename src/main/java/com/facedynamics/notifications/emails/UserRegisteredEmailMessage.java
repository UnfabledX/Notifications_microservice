package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.dto.UserRegistered;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
public class UserRegisteredEmailMessage extends EmailMessage {

    public static final String NEW_REGISTRATION = "Your registration for FaceDynamics social network!";

    @Value("${source.mail.template.user-registered}")
    private String emailTemplate;

    @Override
    public StringWriter getLetterBody() {
        UserRegistered registered = (UserRegistered) receivedDTO.content();

        VelocityContext context = new VelocityContext();
        context.put("ownerName", registered.getOwnerName());
        context.put("email", registered.getEmail());
        context.put("link", registered.getConfirmationLink());
        context.put("timeToLive", registered.getTimeToLive());
        context.put("createdAt", receivedDTO.createdAt());
        Template template = engine.getTemplate(emailTemplate);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer;
    }

    @Override
    public String getLetterSubject(){
        return NEW_REGISTRATION;
    }
}
