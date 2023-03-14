package com.facedynamics.notifications.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationUserServiceDTO {

    private String username;

    private String name;

    private String email;

}