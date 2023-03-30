package com.facedynamics.notifications.events;

import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.NotificationDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PostLikeEvent extends ApplicationEvent {

    private final NotificationDto receivedDTO;
    private final NotificationUserServiceDTO ownerDTO;

    public PostLikeEvent(Object source, NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO) {
        super(source);
        this.receivedDTO = receivedDTO;
        this.ownerDTO = ownerDTO;
    }
}
