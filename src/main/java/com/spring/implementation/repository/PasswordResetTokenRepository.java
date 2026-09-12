package com.spring.implementation.repository;

import com.spring.implementation.entity.PasswordResetTokenEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {

    Optional<PasswordResetTokenEntity> findByTokenHash(
            String tokenHash
    );

    void deleteByKeycloakUserId(String keycloakUserId);

    @Modifying
    @Query("""
                UPDATE PasswordResetTokenEntity t
                SET t.usedAt = :usedAt
                WHERE t.id = :id
                AND t.usedAt IS NULL
                AND t.expiresAt > :now
            """)
    int consumeToken(
            @Param("id") Long id,
            @Param("usedAt") LocalDateTime usedAt,
            @Param("now") LocalDateTime now
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
                SELECT t
                FROM PasswordResetTokenEntity t
                WHERE t.tokenHash = :tokenHash
            """)
    Optional<PasswordResetTokenEntity> findByTokenHashForUpdate(
            @Param("tokenHash") String tokenHash
    );

    @Modifying
    @Query("""
                DELETE FROM PasswordResetTokenEntity t
                WHERE t.keycloakUserId = :keycloakUserId
                  AND t.usedAt IS NULL
            """)
    int deleteActiveTokens(
            @Param("keycloakUserId") String keycloakUserId
    );
}
