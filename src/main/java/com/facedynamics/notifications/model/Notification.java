package com.facedynamics.notifications.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Component
@Table(name = "notifications", schema = "notifications_db")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Notification {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "createdBy_id")
    private Long createdById;

    @Column(name = "notification_createdAt")
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime notificationCreatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id", referencedColumnName = "details_id")
    @JsonProperty("content")
    private NotificationDetails details;
}
