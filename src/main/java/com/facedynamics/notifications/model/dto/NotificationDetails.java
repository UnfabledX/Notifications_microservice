package com.facedynamics.notifications.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY,
             content = JsonInclude.Include.NON_NULL)
public class NotificationDetails {

    private int userId;
    private String userName;
    private String userEmail;
    private String link;
    private int timeToLive;
    private String commentText;
    private String postText;
    private String subscriptionType;
    private LocalDateTime createdAt;

}
