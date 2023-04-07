package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.UserRegistered;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

import static com.facedynamics.notifications.utils.TimeConverter.convert;

@Component
public class UserRegisteredEmailMessage extends EmailMessage {

    public static final String NEW_REGISTRATION = "Your registration for FaceDynamics social network!";

    private final String emailTemplate;

    public UserRegisteredEmailMessage(@Value("${source.mail.template.user-registered}") String emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    @Override
    public StringWriter getLetterBody() {
        NotificationContent<UserRegistered> userRegistered = receivedDTO.content();
        UserRegistered registered = userRegistered.getChild();

        VelocityContext context = new VelocityContext();
        context.put("ownerName", registered.getOwnerName());
        context.put("email", registered.getEmail());
        context.put("link", registered.getConfirmationLink());
        context.put("timeToLive", registered.getTimeToLive());
        context.put("createdAt", convert(registered.getEntityCreatedAt()));
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
