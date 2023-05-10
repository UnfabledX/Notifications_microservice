package com.facedynamics.notifications.config;

import com.facedynamics.notifications.dto.NotificationContent;
import com.facedynamics.notifications.services.commands.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.facedynamics.notifications.dto.NotificationContent.Type.*;

@Configuration
@RequiredArgsConstructor
public class ProcessorConfiguration {

    private final UserRegisteredNotificationProcessor userRegisteredNotificationProcessor;
    private final PostCommentedNotificationProcessor postCommentedNotificationProcessor;
    private final CommentRepliedNotificationProcessor commentRepliedNotificationProcessor;
    private final WaitingApprovalNotificationProcessor waitingApprovalNotificationProcessor;
    private final FollowedByNotificationProcessor followedByNotificationProcessor;
    private final PostLikedNotificationProcessor postLikedNotificationProcessor;
    private final PostDislikedNotificationProcessor postDislikedNotificationProcessor;
    private final CommentLikedNotificationProcessor commentLikedNotificationProcessor;
    private final CommentDislikedNotificationProcessor commentDislikedNotificationProcessor;

    @Bean(name = "processor")
    public Map<NotificationContent.Type, NotificationProcessor> getProcessor() {
        return Map.of(
                USER_REGISTERED,    userRegisteredNotificationProcessor,
                POST_COMMENTED,     postCommentedNotificationProcessor,
                COMMENT_REPLIED,    commentRepliedNotificationProcessor,
                WAITING_APPROVE,    waitingApprovalNotificationProcessor,
                FOLLOWS,            followedByNotificationProcessor,
                POST_LIKED,         postLikedNotificationProcessor,
                POST_DISLIKED,      postDislikedNotificationProcessor,
                COMMENT_LIKED,      commentLikedNotificationProcessor,
                COMMENT_DISLIKED,   commentDislikedNotificationProcessor
        );
    }
}
