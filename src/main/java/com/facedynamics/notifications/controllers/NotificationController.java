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
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.facedynamics.notifications.controllers.NotificationController.NOTIFICATION;
import static com.facedynamics.notifications.utils.Constants.GREATER_THAN_OR_EQUAL_TO_1;
import static com.facedynamics.notifications.utils.Converter.getResponse;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(NOTIFICATION)
public class NotificationController {

    public static final String NOTIFICATION = "/api/v1/notifications";
    private final NotificationService notificationService;

    @GetMapping("/users/{userId}")
    public Map<String, Object> getAllNotificationsByUserId(
            @PathVariable("userId") @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1) Long ownerUserId,
            @PageableDefault(size = 5, sort = "createdAt", direction = DESC) Pageable pageable) {
        Page<Notification> page = notificationService.getAllNotificationsByUserId(ownerUserId, pageable);
        return getResponse(page);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteAllNotificationsByUserId(
            @PathVariable("userId") @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1) Long ownerId) {
        notificationService.deleteAllNotificationsByOwnerId(ownerId);
    }

    @DeleteMapping("/{notificationId}")
    public Long deleteNotificationById(
            @PathVariable @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1) Long notificationId) {
        return notificationService.deleteNotificationById(notificationId);
    }

    @PostMapping
    public NotificationResponseDTO createNotification(@RequestBody @Valid NotificationDto receivedDTO) {
        return notificationService.createNotification(receivedDTO);
    }
}
