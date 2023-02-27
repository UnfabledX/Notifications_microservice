package com.facedynamics.notifications.utils.emails;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.StringWriter;

public class ReplyEmailMessage extends EmailMessage {

    @Override
    public StringWriter getLetterBody() {
        String replyBody = receivedDTO.getDetails().getReplyText();
        String commentBody = receivedDTO.getDetails().getCommentText();

        VelocityContext context = new VelocityContext();
        context.put("ownerName", ownerDTO.getName());
        context.put("triggererUsername", triggerUserName);
        context.put("replyBody", replyBody.length() > 40 ? replyBody.substring(0, 40) : replyBody);
        context.put("commentBody", commentBody.length() > 50 ? commentBody.substring(0, 50) : commentBody);
        context.put("replyCreatedAt", receivedDTO.getDetails().getCreatedAt());
        Template template = engine.getTemplate("src/main/resources/velocity/email-reply.vm");
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer;
    }
}
