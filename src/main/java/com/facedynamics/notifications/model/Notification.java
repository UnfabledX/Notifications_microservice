package com.facedynamics.notifications.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Component
@JsonInclude(value = JsonInclude.Include.NON_EMPTY,
             content = JsonInclude.Include.NON_NULL)
@Table(name = "notifications", schema = "notification_db")
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
    private int ownerId;

    @Column(name = "triggerer_id")
    private Integer triggererId;

    @Column(name = "createdAt")
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "notification_type_id")
    private NotificationType notificationTypeId;
}
