package com.facedynamics.notifications.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationUserServiceDTO {

    private String username;

    private String ownerName;

    private String email;

}
