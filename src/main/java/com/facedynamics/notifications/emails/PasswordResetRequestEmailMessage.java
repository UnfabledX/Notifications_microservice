package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.dto.PasswordResetRequest;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Value;

import java.io.StringWriter;

public class PasswordResetRequestEmailMessage extends EmailMessage {

    public static final String NEW_RESET_PASSWORD = "Request to Reset Password!";

    @Value("${source.mail.template.password-reset-request}")
    private String emailTemplate;

    @Override
    public StringWriter getLetterBody() {
        PasswordResetRequest passwordResetRequest = (PasswordResetRequest) receivedDTO.content();

        VelocityContext context = new VelocityContext();
        context.put("ownerName", passwordResetRequest.getOwnerName());
        context.put("createdAt", receivedDTO.createdAt());
        context.put("link", passwordResetRequest.getConfirmationLink());
        context.put("timeToLive", passwordResetRequest.getTimeToLive());
        Template template = engine.getTemplate(emailTemplate);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer;
    }

    @Override
    public String getLetterSubject(){
        return NEW_RESET_PASSWORD;
    }
}
