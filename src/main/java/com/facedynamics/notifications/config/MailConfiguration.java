package com.facedynamics.notifications.config;

import com.facedynamics.notifications.emails.*;
import com.facedynamics.notifications.dto.NotificationContent;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.facedynamics.notifications.dto.NotificationContent.Type.*;

@Configuration
@RequiredArgsConstructor
public class MailConfiguration {

    @Bean(name = "engine")
    public VelocityEngine getVelocityEngine(){
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADERS,"classpath");
        engine.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        engine.init();
        return engine;
    }

    @Bean(name = "mails")
    public Map<NotificationContent.Type, EmailMessage> getMails(
            UserRegisteredEmailMessage userRegisteredEmailMessage,
            PostCommentedEmailMessage postCommentedEmailMessage,
            CommentRepliedEmailMessage commentRepliedEmailMessage,
            FollowedByEmailMessage followedByEmailMessage,
            WaitingApprovalEmailMessage waitingApprovalEmailMessage,
            PostLikedEmailMessage postLikedEmailMessage,
            PostDislikedEmailMessage postDislikedEmailMessage,
            CommentLikedEmailMessage commentLikedEmailMessage,
            CommentDislikedEmailMessage commentDislikedEmailMessage
            ) {
        return Map.of(
                USER_REGISTERED,    userRegisteredEmailMessage,
                POST_COMMENTED,     postCommentedEmailMessage,
                COMMENT_REPLIED,    commentRepliedEmailMessage,
                FOLLOWS,            followedByEmailMessage,
                WAITING_APPROVE,    waitingApprovalEmailMessage,
                POST_LIKED,         postLikedEmailMessage,
                POST_DISLIKED,      postDislikedEmailMessage,
                COMMENT_LIKED,      commentLikedEmailMessage,
                COMMENT_DISLIKED,   commentDislikedEmailMessage
        );
    }
}

