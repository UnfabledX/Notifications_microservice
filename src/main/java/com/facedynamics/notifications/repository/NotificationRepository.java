package com.facedynamics.notifications.repository;

import com.facedynamics.notifications.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    boolean existsByOwnerId(Long ownerId);

    Page<Notification> findAllByOwnerId(Long ownerId, Pageable pageable);

    void deleteNotificationsByOwnerId(Long ownerId);

    void deleteNotificationById(long Id);

    Optional<Notification> findNotificationById(long Id);

}
