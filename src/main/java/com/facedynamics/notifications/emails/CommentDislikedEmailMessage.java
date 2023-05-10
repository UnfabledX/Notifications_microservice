package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.dto.CommentDisliked;
import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.dto.NotificationUserServiceDTO;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.List;

import static com.facedynamics.notifications.utils.TimeConverter.convert;

@Component
public class CommentDislikedEmailMessage extends EmailMessage {

    public static final String NEW_COMMENT_DISLIKED = "Received NEW Dislikes to the comment!";
    private final Long delay;
    private final String emailTemplate;

    public CommentDislikedEmailMessage( VelocityEngine engine,
                                        @Value("${source.mail.islike.delaymillis}")Long delay,
                                        @Value("${source.mail.template.comment-disliked}")String emailTemplate) {
        super(engine);
        this.delay = delay;
        this.emailTemplate = emailTemplate;
    }

    @Override
    public StringWriter getLetterBody(NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO, Object payload) {
        if (receivedDTO.content() instanceof  CommentDisliked commentDisliked) {
            String commentText = commentDisliked.getCommentText();
            int size = 0;
            if (payload instanceof List<?> list) {
                size = list.size();
            }
            VelocityContext context = new VelocityContext();
            context.put("ownerName", ownerDTO.getOwnerName());
            context.put("commentBody", commentText.length() > 50 ? commentText.substring(0, 50) : commentText);
            context.put("numberOfDislikes", size);
            context.put("times", size == 1 ? "time" : "times");
            context.put("min", delay / 60000);
            context.put("minutes", delay / 60000 == 1 ? "minute" : "minutes");
            context.put("likeCreatedAt", convert(commentDisliked.getEntityCreatedAt()));
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
        return NEW_COMMENT_DISLIKED;
    }
}