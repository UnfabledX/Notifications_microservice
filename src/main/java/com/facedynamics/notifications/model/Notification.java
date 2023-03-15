package com.facedynamics.notifications.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.facedynamics.notifications.utils.Constants.GREATER_THAN_OR_EQUAL_TO_1;

@Entity
@Component
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
    @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1)
    private Long ownerId;

    @Column(name = "createdBy_id")
    @Min(value = 1, message = GREATER_THAN_OR_EQUAL_TO_1)
    private Long createdById;

    @Column(name = "createdAt")
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id", referencedColumnName = "id")
    private NotificationDetails details;
}
