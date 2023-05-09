package com.facedynamics.notifications.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationUserServiceDTO {

    private String username;

    @JsonProperty("name")
    private String ownerName;

    private String email;
}
