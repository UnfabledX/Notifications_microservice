package com.facedynamics.notifications.controllers;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.services.NotificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class NotificationController {

    public static final String GREATER_THAN_OR_EQUAL_TO_1 = "The value must be greater than or equal to 1";

    private final NotificationService notificationService;

    @GetMapping("/users/{userId}/notifications")
    @PreAuthorize("(hasAuthority('USER') and @auth.hasId(#ownerUserId)) or hasAuthority('ADMIN')")
    public Page<Notification> getAllNotificationsByUserId(
            @PathVariable("userId") @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1) Long ownerUserId,
            @PageableDefault Pageable pageable) {
        return notificationService.getAllNotificationsByUserId(ownerUserId, pageable);
    }

    @DeleteMapping("/users/{userId}/notifications")
    @PreAuthorize("(hasAuthority('USER') and @auth.hasId(#ownerId)) or hasAuthority('ADMIN')")
    public void deleteAllNotificationsByUserId(
            @PathVariable("userId") @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1) Long ownerId) {
        notificationService.deleteAllNotificationsByOwnerId(ownerId);
    }

    @DeleteMapping("/notifications/{notificationId}")
    @PreAuthorize("(hasAuthority('USER') and @auth.belongsToUser(#notificationId)) or hasAuthority('ADMIN')")
    public Long deleteNotificationById(
            @PathVariable @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1) Long notificationId) {
        return notificationService.deleteNotificationById(notificationId);
    }

    @PostMapping("/notifications")
    @PreAuthorize("(hasAuthority('USER') and @auth.hasId(#receivedDTO.createdById())) or hasAuthority('ADMIN')")
    public NotificationDto createNotification(@RequestBody @Valid NotificationDto receivedDTO) {
        return notificationService.createNotification(receivedDTO);
    }
}
