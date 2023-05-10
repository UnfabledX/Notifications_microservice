package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.dto.NotificationUserServiceDTO;
import com.facedynamics.notifications.dto.PostDisliked;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.List;

import static com.facedynamics.notifications.utils.TimeConverter.convert;

@Component
public class PostDislikedEmailMessage extends EmailMessage {

    public static final String NEW_POST_DISLIKED = "Received NEW Dislikes to the post!";

    private final Long delay;
    private final String emailTemplate;

    public PostDislikedEmailMessage(@Value("${source.mail.islike.delaymillis}") Long delay,
                                    @Value("${source.mail.template.post-disliked}") String emailTemplate,
                                    VelocityEngine engine) {
        super(engine);
        this.delay = delay;
        this.emailTemplate = emailTemplate;
    }

    @Override
    public StringWriter getLetterBody(NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO, Object payload) {
        if (receivedDTO.content() instanceof PostDisliked postDisliked) {
            String postBody = postDisliked.getPostText();
            int size = 0;
            if (payload instanceof List<?> list) {
                size = list.size();
            }
            VelocityContext context = new VelocityContext();
            context.put("ownerName", ownerDTO.getOwnerName());
            context.put("postBody", postBody.length() > 50 ? postBody.substring(0, 50) : postBody);
            context.put("numberOfDislikes", size);
            context.put("times", size == 1 ? "time" : "times");
            context.put("min", delay / 60000);
            context.put("minutes", delay / 60000 == 1 ? "minute" : "minutes");
            context.put("likeCreatedAt", convert(postDisliked.getEntityCreatedAt()));
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
        return NEW_POST_DISLIKED;
    }
}