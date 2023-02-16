package com.facedynamics.notifications.controllers;

import com.facedynamics.notifications.model.dto.NotificationUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:7111/")
public interface UserServiceConsumer {
    /**
     * Receives user information such as:
     * name and email.
     * @param id user Id
     * @return user object with necessary information.
     */
    @GetMapping("users/{id}")
    NotificationUserDTO getUserById(@PathVariable int id);
}