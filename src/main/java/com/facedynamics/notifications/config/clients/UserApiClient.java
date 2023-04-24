package com.facedynamics.notifications.config.clients;

import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "users-service")
@Component
public interface UserApiClient {
    /**
     * Receives user information such as:
     * name, username and email.
     * @param id user Id
     * @return user object with necessary information.
     */
    @GetMapping("/users/{id}")
    NotificationUserServiceDTO getById(@PathVariable Long id);
}
