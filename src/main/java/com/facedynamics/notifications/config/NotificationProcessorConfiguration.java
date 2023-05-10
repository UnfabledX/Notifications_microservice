package com.facedynamics.notifications.config;

import com.facedynamics.notifications.dto.NotificationContent;
import com.facedynamics.notifications.services.commands.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.facedynamics.notifications.dto.NotificationContent.Type.*;
import static com.facedynamics.notifications.dto.NotificationContent.Type.COMMENT_DISLIKED;
import static com.facedynamics.notifications.dto.NotificationContent.Type.COMMENT_LIKED;
import static com.facedynamics.notifications.dto.NotificationContent.Type.POST_DISLIKED;

@Configuration
@RequiredArgsConstructor
public class NotificationProcessorConfiguration {

    @Bean
    public Map<NotificationContent.Type, NotificationProcessor> getProcessor(
            UserRegisteredNotificationProcessor userRegisteredNotificationProcessor,
            PostCommentedNotificationProcessor postCommentedNotificationProcessor,
            CommentRepliedNotificationProcessor commentRepliedNotificationProcessor,
            FollowedByNotificationProcessor followedByNotificationProcessor,
            WaitingApprovalNotificationProcessor waitingApprovalNotificationProcessor,
            PostLikedNotificationProcessor postLikedNotificationProcessor,
            PostDislikedNotificationProcessor postDislikedNotificationProcessor,
            CommentLikedNotificationProcessor commentLikedNotificationProcessor,
            CommentDislikedNotificationProcessor commentDislikedNotificationProcessor
    ) {
        return Map.of(
                USER_REGISTERED,    userRegisteredNotificationProcessor,
                POST_COMMENTED,     postCommentedNotificationProcessor,
                COMMENT_REPLIED,    commentRepliedNotificationProcessor,
                FOLLOWS,            followedByNotificationProcessor,
                WAITING_APPROVE,    waitingApprovalNotificationProcessor,
                POST_LIKED,         postLikedNotificationProcessor,
                POST_DISLIKED,      postDislikedNotificationProcessor,
                COMMENT_LIKED,      commentLikedNotificationProcessor,
                COMMENT_DISLIKED,   commentDislikedNotificationProcessor
        );
    }
}
