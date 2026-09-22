package com.spring.implementation.entity;

import com.spring.implementation.dto.LocationDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.util.UUID;

@Entity
@Table(name = "location")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JdbcTypeCode(java.sql.Types.CHAR)
    @Column(name = "uuid", nullable = false, length = 36)
    private UUID uuid;

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "npi", length = 50)
    private String npi;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "id")
    private AddressEntity billingAddress;

    @OneToOne
    @JoinColumn(name = "tax_entity_id", referencedColumnName = "id")
    private TaxEntity taxEntity;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }

        if (isActive == null) {
            isActive = true;
        }
    }

    public static LocationDTO toDto(LocationEntity location) {
        if (location == null) {
            return null;
        }

        return LocationDTO.builder()
                .uuid(location.getUuid())
                .code(location.getCode())
                .name(location.getName())
                .phone(location.getPhone())
                .email(location.getEmail())
                .npi(location.getNpi())
                .billingAddress(AddressEntity.toDto(location.getBillingAddress()))
                .taxEntity(TaxEntity.toDto(location.getTaxEntity()))
                .build();
    }
}