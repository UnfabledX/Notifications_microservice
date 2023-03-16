package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.dto.UserRegistered;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.StringWriter;

public class UserRegisteredEmailMessage extends EmailMessage {

    public static final String NEW_REGISTRATION = "Your registration for FaceDynamics social network!";

    @Override
    public StringWriter getLetterBody() {
        UserRegistered registered = (UserRegistered) receivedDTO.content();

        VelocityContext context = new VelocityContext();
        context.put("ownerName", registered.getOwnerName());
        context.put("email", registered.getEmail());
        context.put("link", registered.getConfirmationLink());
        context.put("timeToLive", registered.getTimeToLive());
        context.put("createdAt", receivedDTO.createdAt());
        Template template = engine.getTemplate("src/main/resources/velocity/email-user-registered.vm");
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer;
    }

    @Override
    public String getLetterSubject(){
        return NEW_REGISTRATION;
    }
}
