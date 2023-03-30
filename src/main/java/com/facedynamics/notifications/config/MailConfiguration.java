package com.facedynamics.notifications.config;

import com.facedynamics.notifications.emails.*;
import com.facedynamics.notifications.model.dto.NotificationContent;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Map;
import java.util.Properties;

import static com.facedynamics.notifications.model.dto.NotificationContent.Type.*;

@Configuration
public class MailConfiguration {

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("alex0destroyer@gmail.com");
        mailSender.setPassword("hdrvtiqkponeeouv");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.connectiontimeout", 5000);
        props.put("mail.smtp.timeout", 3000);
        props.put("mail.smtp.writetimeout", 5000);

        return mailSender;
    }

    @Bean(name = "engine")
    public VelocityEngine getVelocityEngine(){
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADERS,"classpath");
        engine.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        engine.init();
        return engine;
    }

    @Autowired private UserRegisteredEmailMessage userRegisteredEmailMessage;
    @Autowired private PostCommentedEmailMessage postCommentedEmailMessage;
    @Autowired private CommentRepliedEmailMessage commentRepliedEmailMessage;
    @Autowired private FollowedByEmailMessage followedByEmailMessage;
    @Autowired private PostLikedEmailMessage postLikedEmailMessage;
    @Autowired private PostDislikedEmailMessage postDislikedEmailMessage;
    @Autowired private CommentLikedEmailMessage commentLikedEmailMessage;
    @Autowired private CommentDislikedEmailMessage commentDislikedEmailMessage;

    @Bean(name = "mails")
    public Map<NotificationContent.Type, EmailMessage> getMails(){
        return Map.of(
                USER_REGISTERED,                userRegisteredEmailMessage,
                POST_COMMENTED,                 postCommentedEmailMessage,
                COMMENT_REPLIED,                commentRepliedEmailMessage,
                FOLLOWED_BY,                    followedByEmailMessage,
                POST_LIKED,                     postLikedEmailMessage,
                POST_DISLIKED,                  postDislikedEmailMessage,
                COMMENT_LIKED,                  commentLikedEmailMessage,
                COMMENT_DISLIKED,               commentDislikedEmailMessage
        );
    }
}

