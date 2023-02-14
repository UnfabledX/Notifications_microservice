package com.facedynamics.notifications.controllers;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/users/{userId}")
    public List<Notification> getAllNotificationsByUserId(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @PathVariable("userId") @Min(1) int ownerUserId) {
        return notificationService.getAllNotificationsByUserId(page, ownerUserId);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteAllNotificationsByUserId(@PathVariable("userId") @Min(1) int ownerId) {
        notificationService.deleteAllNotificationsByOwnerId(ownerId);
    }

    @DeleteMapping("/{notificationId}")
    public Long deleteNotificationById(
            @PathVariable @Min(1) long notificationId) {
        return notificationService.deleteNotificationById(notificationId);
    }
}
