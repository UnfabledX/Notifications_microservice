package com.facedynamics.notifications.controllers;

import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${mock.server.postman}")
public interface UserEventService {
    /**
     * Receives user information such as:
     * name, username and email.
     * @param id user Id
     * @return user object with necessary information.
     */
    @GetMapping("users/{id}")
    NotificationUserServiceDTO getUserById(@PathVariable long id);
}
