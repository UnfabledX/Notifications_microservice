package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.events.PostDislikeEvent;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.PostDisliked;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.List;

@Component
public class PostDislikedEmailMessage extends EmailMessage {

    public static final String NEW_POST_DISLIKED = "Received NEW Dislikes to the post!";

    private final Long delay;

    private final String emailTemplate;

    public PostDislikedEmailMessage(@Value("${source.mail.islike.delaymillis}") Long delay,
                                    @Value("${source.mail.template.post-disliked}") String emailTemplate) {
        this.delay = delay;
        this.emailTemplate = emailTemplate;
    }

    @Override
    public StringWriter getLetterBody() {
        NotificationContent<PostDisliked> content = receivedDTO.content();
        PostDisliked postDisliked = content.getChild();
        String postBody = postDisliked.getPostText();
        List<PostDislikeEvent> events = (List<PostDislikeEvent>) payload;

        VelocityContext context = new VelocityContext();
        context.put("ownerName", ownerDTO.getOwnerName());
        context.put("postBody", postBody.length() > 50 ? postBody.substring(0, 50) : postBody);
        context.put("numberOfDislikes", events.size());
        context.put("times", events.size() == 1 ? "time" : "times");
        context.put("min", delay / 60000);
        context.put("minutes", delay / 60000 == 1 ? "minute" : "minutes");
        context.put("likeCreatedAt", postDisliked.getEntityCreatedAt());
        Template template = engine.getTemplate(emailTemplate);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer;
    }

    @Override
    public String getLetterSubject() {
        return NEW_POST_DISLIKED;
    }
}