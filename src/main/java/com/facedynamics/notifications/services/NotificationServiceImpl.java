package com.facedynamics.notifications.services;

import com.facedynamics.notifications.controllers.UserEventService;
import com.facedynamics.notifications.handler.NotFoundException;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationType;
import com.facedynamics.notifications.model.dto.NotificationGetDTO;
import com.facedynamics.notifications.model.dto.NotificationResponseDTO;
import com.facedynamics.notifications.model.dto.NotificationUserServiceDTO;
import com.facedynamics.notifications.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.facedynamics.notifications.model.NotificationType.getType;
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
    public Page<Notification> getAllNotificationsByUserId(int page, int ownerId) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        if (notificationRepository.existsByOwnerId(ownerId)) {
            return notificationRepository.findAllByOwnerId(ownerId, pageable);
        } else {
            throw new NotFoundException(USER_IS_NOT_FOUND, ownerId);
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
            throw new NotFoundException(USER_IS_NOT_FOUND, ownerId);
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
            throw new NotFoundException(ID_OF_THE_NOTIFICATION_IS_NOT_FOUND, Id);
        }
        notificationRepository.deleteNotificationById(Id);
        return notification.get().getId();
    }

    @Override
    public NotificationResponseDTO createNotification(NotificationGetDTO receivedDTO) {
        NotificationType type = getType(receivedDTO.getNotificationType());
        saveNotificationToDatabase(receivedDTO, type);

        switch (type) {
            case REGISTRATION: //todo
            case RESET_PASSWORD: //todo
            case COMMENT, REPLY:
                return sendCommentReplyNotificationReturnDTO(receivedDTO, type);
            case FOLLOW:  //todo
            case SUBSCRIPTION: //todo
        }
        return null;
    }

    private NotificationResponseDTO sendCommentReplyNotificationReturnDTO(NotificationGetDTO receivedDTO,
                                                                          NotificationType type) {
        int triggerUserId = receivedDTO.getDetails().getUserId();
        LocalDateTime createdAt = receivedDTO.getDetails().getCreatedAt();
        int ownerId = receivedDTO.getOwnerId();

        NotificationUserServiceDTO ownerDTO = userEventService.getUserById(ownerId);
        NotificationUserServiceDTO triggerUserDTO = userEventService.getUserById(triggerUserId);

        emailService.sendEmail(receivedDTO, ownerDTO, triggerUserDTO.getUsername());

        return new NotificationResponseDTO(triggerUserDTO.getUsername(),
                type, createdAt);
    }

    private void saveNotificationToDatabase(NotificationGetDTO receivedDTO, NotificationType type) {
        int triggerUserId = receivedDTO.getDetails().getUserId();
        LocalDateTime createdAt = receivedDTO.getDetails().getCreatedAt();
        int ownerId = receivedDTO.getOwnerId();
        Notification notification = Notification.builder()
                .ownerId(ownerId)
                .triggererId(triggerUserId)
                .createdAt(createdAt)
                .notificationType(type.getId())
                .build();
        notificationRepository.save(notification);
    }
}
