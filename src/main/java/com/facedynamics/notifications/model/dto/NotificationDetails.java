package com.facedynamics.notifications.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY,
             content = JsonInclude.Include.NON_NULL)
public class NotificationDetails {
    @Min(1)
    private int userId;
    @Size(min = 3, max = 24, message =  "Username mustn't be bigger then 24 letters and less then 3 letters")
    private String userName;
    @Email(message = "Email is written in a wrong format")
    private String userEmail;
    private String link;
    @Positive
    private int timeToLive;
    private String commentText;
    private String replyText;
    private String postText;
    private String subscriptionType;
    private LocalDateTime createdAt;

}
