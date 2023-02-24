package com.facedynamics.notifications.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
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
    @Min(value = 1, message = "Id must be greater than 1")
    private int ownerId;

    @Column(name = "triggerer_id")
    @Min(value = 1, message = "Id must be greater than 1")
    private Integer triggererId;

    @Column(name = "createdAt")
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "notification_type_id")
    private int notificationType;
}
