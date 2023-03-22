package com.facedynamics.notifications.config;

import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.services.commands.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.facedynamics.notifications.model.dto.NotificationContent.Type.*;

@Configuration
public class ProcessorConfiguration {

    @Bean(name = "processor")
    public Map<NotificationContent.Type, AbstractNotificationProcessor> getProcessor(){
        return Map.of(
                USER_REGISTERED,                new UserRegisteredNotificationProcessor(),
                USER_PASSWORD_RESET_REQUEST,    new PasswordResetRequestProcessor(),
                POST_COMMENTED,                 new PostCommentedNotificationProcessor(),
                COMMENT_REPLIED,                new CommentRepliedNotificationProcessor(),
                SUBSCRIBED_BY,                  new SubscribedByNotificationProcessor(),
                FOLLOWED_BY,                    new FollowedByNotificationProcessor()
        );
    }
}
