package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.WaitingApproval;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

import static com.facedynamics.notifications.utils.TimeConverter.convert;

@Component
public class WaitingApprovalEmailMessage extends EmailMessage {

    public static final String NEW_FOLLOW_APPROVAL = "You have a NEW Follow to approve!";

    private final String emailTemplate;

    public WaitingApprovalEmailMessage(@Value("${source.mail.template.waiting-approval}") String emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    @Override
    public StringWriter getLetterBody() {
        NotificationContent<WaitingApproval> content = receivedDTO.content();
        WaitingApproval created = content.getChild();

        VelocityContext context = new VelocityContext();
        context.put("ownerName", created.getRecipientName());
        context.put("triggererUsername", created.getCreatedByName());
        context.put("followCreatedAt", convert(created.getEntityCreatedAt()));
        Template template = engine.getTemplate(emailTemplate);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer;
    }

    @Override
    public String getLetterSubject(){
        return NEW_FOLLOW_APPROVAL;
    }
}
