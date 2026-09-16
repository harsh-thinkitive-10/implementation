package com.spring.implementation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "admin")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @JdbcTypeCode(Types.CHAR)
    @Column(name = "uuid", nullable = false, length = 36)
    private UUID uuid;

    @Column(name = "keycloak_user_id", nullable = false)
    private String keycloakUserId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false)
    private String email;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}