package com.facedynamics.notifications.controllers;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationResponseDTO;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.services.NotificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.facedynamics.notifications.utils.Constants.GREATER_THAN_OR_EQUAL_TO_1;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/users/{userId}")
    public Page<Notification> getAllNotificationsByUserId(
            @PathVariable("userId") @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1) int ownerUserId,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return notificationService.getAllNotificationsByUserId(ownerUserId, pageable);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteAllNotificationsByUserId(
            @PathVariable("userId") @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1) int ownerId) {
        notificationService.deleteAllNotificationsByOwnerId(ownerId);
    }

    @DeleteMapping("/{notificationId}")
    public Long deleteNotificationById(
            @PathVariable @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1) long notificationId) {
        return notificationService.deleteNotificationById(notificationId);
    }

    @PostMapping
    public NotificationResponseDTO createNotification(@RequestBody @Valid NotificationDto receivedDTO) {
        return notificationService.createNotification(receivedDTO);
    }
}
