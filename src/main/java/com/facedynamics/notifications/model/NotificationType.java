package com.facedynamics.notifications.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.stereotype.Component;

@Entity
@Component
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications_types", schema = "notification_db")
public class NotificationType {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.SMALLINT)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @Column(name = "text_body", nullable = false)
    private String textBody;
}
