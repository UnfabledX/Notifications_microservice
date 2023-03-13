package com.facedynamics.notifications.services;

import com.facedynamics.notifications.controllers.UserEventService;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

import static com.facedynamics.notifications.utils.Constants.*;

/**
 * Service class that is responsible for business logic of
 * notification processing.
 *
 * @author osynelnyk
 */
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserEventService userEventService;

    private final EmailService emailService;

    /**
     * Finds all notifications of specified user ID.
     *
     * @param ownerId ID of the user who owns his notifications
     * @return the list of found notifications or response HTTP.NOT_FOUND
     * if ID of the user is not found in the database.
     */
    @Override
    public List<Notification> getAllNotificationsByUserId(int page, int ownerId) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        if (notificationRepository.existsByOwnerId(ownerId)) {
            Page<Notification> notifications = notificationRepository.findAllByOwnerId(ownerId, pageable);
            return notifications.getContent();
        } else {
            throw new ValidationException(ID_OR_PAGE_IS_NOT_FOUND);
        }
    }

    /**
     * Deletes all notifications of specified user ID.
     *
     * @param ownerId ID of the user who owns his notifications
     */
    @Override
    public void deleteAllNotificationsByOwnerId(int ownerId) {
        if (notificationRepository.existsByOwnerId(ownerId)) {
            notificationRepository.deleteNotificationsByOwnerId(ownerId);
        } else {
            throw new ValidationException(USER_IS_NOT_FOUND);
        }
    }

    /**
     * Deletes the concrete notification of specified ID.
     *
     * @param Id Notification ID which must be deleted
     * @return ID of the deleted notification if operation is successful
     * or response HTTP.NOT_FOUND if ID of the notification is not
     * found in the database.
     */
    @Override
    public Long deleteNotificationById(long Id) {
        Optional<Notification> notification = notificationRepository.findNotificationById(Id);
        if (notification.isEmpty()) {
            throw new ValidationException(ID_OF_THE_NOTIFICATION_IS_NOT_FOUND);
        }
        notificationRepository.deleteNotificationById(Id);
        return notification.get().getId();
    }

    @Override
    public Notification createNotification(NotificationDto receivedDTO) {
        NotificationContent.Type type = receivedDTO.content().getType();

        switch (type) {
            case USER_REGISTERED: //todo
            case PASSWORD_RESET: //todo
            case COMMENT_CREATED:
            case COMMENT_REPLIED: {
                NotificationUserServiceDTO ownerDTO = userEventService.getUserById(receivedDTO.recipientId());
                NotificationUserServiceDTO triggerUserDTO = userEventService.getUserById(receivedDTO.createdById());
                emailService.sendEmail(receivedDTO, ownerDTO, triggerUserDTO.getUsername());
                Notification notification = Notification.builder()
                        .ownerId(receivedDTO.recipientId())
                        .triggererId(receivedDTO.createdById())
                        .createdAt(receivedDTO.createdAt())
                        .updatedAt(receivedDTO.updatedAt())
                        .notificationType(receivedDTO.content().getType().ordinal())
                        .build();
                return notificationRepository.save(notification);
            }
            case FOLLOWED_BY:  //todo
            case SUBSCRIBED_BY: //todo
        }
        return null;
    }

}
