package com.facedynamics.notifications.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static com.facedynamics.notifications.utils.Constants.GREATER_THAN_OR_EQUAL_TO_1;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY,
             content = JsonInclude.Include.NON_NULL)
public class NotificationDetails {
    @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1)
    private int userId;
    @Size(min = 3, max = 24, message =  "Username mustn't be bigger then 24 letters and less then 3 letters")
    private String userName;
    @Email(message = "Email is written in a wrong format")
    private String userEmail;
    private String link;
    @PositiveOrZero
    private int timeToLive;
    private String commentText;
    private String replyText;
    private String postText;
    private String subscriptionType;
    private LocalDateTime createdAt;

}
