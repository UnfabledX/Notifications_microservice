package com.facedynamics.notifications.config;

import com.facedynamics.notifications.emails.*;
import com.facedynamics.notifications.model.dto.NotificationContent;
import org.apache.velocity.app.VelocityEngine;
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

    @Bean(initMethod = "init")
    public VelocityEngine getVelocityEngine(){
        return new VelocityEngine();
    }

    @Bean(name = "mails")
    public Map<NotificationContent.Type, EmailMessage> getMails(){
        return Map.of(
                USER_REGISTERED,                new UserRegisteredEmailMessage(),
                USER_PASSWORD_RESET_REQUEST,    new PasswordResetRequestEmailMessage(),
                POST_COMMENTED,                 new PostCommentedEmailMessage(),
                COMMENT_REPLIED,                new CommentRepliedEmailMessage(),
                SUBSCRIBED_BY,                  new SubscribedByEmailMessage(),
                FOLLOWED_BY,                    new FollowedByEmailMessage()
        );
    }
}

