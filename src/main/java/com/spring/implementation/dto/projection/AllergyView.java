package com.spring.implementation.dto.projection;

import java.time.Instant;
import java.util.UUID;

public interface AllergyView {
    UUID getUuid();
    Long getPatientId();
    String getName();
    String getAllergen();
    String getReaction();
    String getSeverity();
    Instant getOnsetDate();
    String getSource();
    String getStatus();
    String getNotes();
}