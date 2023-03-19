package com.facedynamics.notifications.services.commands;

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

    private final Map<String, NotificationProcessor> processor;

    public NotificationProcessor getProcessor(String commandType){
        return processor.get(commandType);
    }
}
