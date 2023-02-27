package com.facedynamics.notifications.services;

import com.facedynamics.notifications.model.dto.NotificationGetDTO;
import com.facedynamics.notifications.model.dto.NotificationUserServiceDTO;
import com.facedynamics.notifications.util.Constants;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.StringWriter;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${source.mail.address}")
    protected String emailFrom;

    protected final JavaMailSender mailSender;

    protected final VelocityEngine engine;

    @Override
    public void sendCommentEmail(NotificationGetDTO receivedDTO, NotificationUserServiceDTO ownerDTO,
                                 String triggerUserName) {
        String commentBody = receivedDTO.getDetails().getCommentText();
        String postBody = receivedDTO.getDetails().getPostText();

        VelocityContext context = new VelocityContext();
        context.put("ownerName", ownerDTO.getName());
        context.put("triggererUsername", triggerUserName);
        context.put("commentBody", commentBody.length() > 40 ? commentBody.substring(0, 40) : commentBody);
        context.put("postBody", postBody.length() > 50 ? postBody.substring(0, 50) : postBody);
        context.put("commentCreatedAt", receivedDTO.getDetails().getCreatedAt());
        Template template = engine.getTemplate("src/main/resources/velocity/email-comment.vm");
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        MimeMessagePreparator prep = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(ownerDTO.getEmail());
            helper.setFrom(emailFrom);
            helper.setSubject(Constants.NEW_COMMENT);
            helper.setText(writer.toString(), true);
        };
        mailSender.send(prep);
    }

    @Override
    public void sendReplyEmail(NotificationGetDTO receivedDTO, NotificationUserServiceDTO ownerDTO,
                                 String triggerUserName) {
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
        
        MimeMessagePreparator prep = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(ownerDTO.getEmail());
            helper.setFrom(emailFrom);
            helper.setSubject(Constants.NEW_REPLY);
            helper.setText(writer.toString(), true);
        };
        this.mailSender.send(prep);
    }
}
