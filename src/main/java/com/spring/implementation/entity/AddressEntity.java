package com.spring.implementation.entity;

import com.spring.implementation.dto.AddressDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.util.UUID;

@Entity
@Table(name = "address")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JdbcTypeCode(java.sql.Types.CHAR)
    @Column(name = "uuid", nullable = false, length = 36)
    private UUID uuid;

    @Column(name = "line1", nullable = false, length = 255)
    private String line1;

    @Column(name = "line2", length = 255)
    private String line2;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "state", nullable = false, length = 100)
    private String state;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @Column(name = "zipcode", nullable = false, length = 20)
    private String zipcode;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }

    public static AddressDTO toDto(AddressEntity address) {
        if (address == null) {
            return null;
        }

        return AddressDTO.builder().uuid(address.getUuid()).line1(address.getLine1()).line2(address.getLine2()).city(address.getCity()).state(address.getState()).country(address.getCountry()).zipcode(address.getZipcode()).build();
    }

    public static AddressEntity toEntity(AddressDTO address) {
        if (address == null) {
            return null;
        }

        return AddressEntity.builder().uuid(address.getUuid()).line1(address.getLine1()).line2(address.getLine2()).city(address.getCity()).state(address.getState()).country(address.getCountry()).zipcode(address.getZipcode()).build();
    }
}