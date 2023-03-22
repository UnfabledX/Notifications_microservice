package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.model.dto.NotificationContent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Class NotificationFactory provides corresponding
 * command depending on what notification is to create.
 *
 * @author Oleksii Synelnyk
 */
@Getter
@Component
@RequiredArgsConstructor
public class NotificationFactory {

    private final Map<NotificationContent.Type, NotificationProcessor> processor;

    public NotificationProcessor getProcessor(NotificationContent.Type commandType){
        return processor.get(commandType);
    }
}
