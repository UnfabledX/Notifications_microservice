package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.dto.NotificationUserServiceDTO;
import com.facedynamics.notifications.dto.WaitingApproval;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

import static com.facedynamics.notifications.utils.TimeConverter.convert;

@Component
public class WaitingApprovalEmailMessage extends EmailMessage {

    public static final String NEW_FOLLOW_APPROVAL = "You have a NEW Follow to approve!";

    private final String emailTemplate;

    public WaitingApprovalEmailMessage(VelocityEngine engine,
                                       @Value("${source.mail.template.waiting-approval}") String emailTemplate) {
        super(engine);
        this.emailTemplate = emailTemplate;
    }

    @Override
    public StringWriter getLetterBody(NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO, Object payload) {
        if (receivedDTO.content() instanceof WaitingApproval approval) {
            VelocityContext context = new VelocityContext();
            context.put("ownerName", approval.getRecipientName());
            context.put("triggererUsername", approval.getCreatedByName());
            context.put("followCreatedAt", convert(approval.getEntityCreatedAt()));
            Template template = engine.getTemplate(emailTemplate);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return writer;
        } else {
            throw new IllegalArgumentException("Wrong type of the notification!");
        }
    }

    @Override
    public String getLetterSubject(){
        return NEW_FOLLOW_APPROVAL;
    }
}
