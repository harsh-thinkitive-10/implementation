package com.spring.implementation.entity;

import com.spring.implementation.dto.enums.TaxIdType;
import jakarta.persistence.*;
import lombok.*;
import com.spring.implementation.dto.TaxDTO;
import org.hibernate.annotations.JdbcTypeCode;

import java.util.UUID;

@Entity
@Table(name = "tax")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JdbcTypeCode(java.sql.Types.CHAR)
    @Column(name = "uuid", nullable = false, length = 36)
    private UUID uuid;

    @Column(name = "tax_id", nullable = false, length = 100)
    private String taxId;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 30)
    private TaxIdType type;

    @Column(name = "npi", length = 50)
    private String npi;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "id")
    private AddressEntity billingAddress;

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

    public static TaxDTO toDto(TaxEntity tax) {
        if (tax == null) {
            return null;
        }

        return TaxDTO.builder()
                .uuid(tax.getUuid())
                .taxId(tax.getTaxId())
                .name(tax.getName())
                .type(tax.getType())
                .npi(tax.getNpi())
                .billingAddress(AddressEntity.toDto(tax.getBillingAddress()))
                .build();
    }
}