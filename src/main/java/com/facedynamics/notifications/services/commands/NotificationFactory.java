package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.controllers.UserEventService;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.repository.NotificationRepository;
import com.facedynamics.notifications.services.EmailService;
import lombok.RequiredArgsConstructor;

/**
 * Class CommandFactory provides corresponding
 * command depending on what notification is to create.
 *
 * @author Oleksii Synelnyk
 */
@RequiredArgsConstructor
public class NotificationFactory {

    private final UserEventService userEventService;
    private final EmailService emailService;
    private final NotificationRepository notificationRepository;

    /**
     * Creates command from enum NotificationContent.Type
     * that fulfills the task of creating specific notifications
     *
     * @param type enum instance which indicates what type of notification
     *             to create.
     * @return Command that corresponds to specific enum.
     */
    public NotificationProcessor createCommand(NotificationContent.Type type) {
        NotificationProcessor processor = null;
        switch (type) {
            case USER_REGISTERED             -> processor = new UserRegisteredCommand();
            case USER_PASSWORD_RESET_REQUEST -> processor = new PasswordResetRequestCommand();
            case POST_COMMENTED              -> processor = new PostCommentedCommand();
            case COMMENT_REPLIED             -> processor = new CommentRepliedCommand();
            case FOLLOWED_BY                 -> processor = new FollowedByCommand();
            case SUBSCRIBED_BY               -> processor = new SubscribedByCommand();
        }
        if (processor != null) {
            processor.setUserEventService(userEventService);
            processor.setEmailService(emailService);
            processor.setNotificationRepository(notificationRepository);
        }
        return processor;
    }
}
