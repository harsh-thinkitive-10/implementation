package com.spring.implementation.service.impl;

import com.spring.implementation.dto.TaxDTO;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.entity.AddressEntity;
import com.spring.implementation.entity.TaxEntity;
import com.spring.implementation.exception.ImplException;
import com.spring.implementation.repository.TaxRepository;
import com.spring.implementation.service.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaxServiceImpl implements TaxService {

    private final TaxRepository taxRepository;

    @Override
    @Transactional
    public TaxDTO createTax(TaxDTO dto) {
        if (taxRepository.existsByTaxIdIgnoreCaseAndIsActiveTrue(dto.getTaxId()))
            throw new RuntimeException("Tax ID already exists");

        TaxEntity tax = TaxEntity.builder()
                .taxId(dto.getTaxId())
                .name(dto.getName())
                .type(dto.getType())
                .npi(dto.getNpi())
                .billingAddress(AddressEntity.toEntity(dto.getBillingAddress()))
                .build();

        return TaxEntity.toDto(taxRepository.save(tax));
    }

    @Override
    public Page<TaxDTO> getAllTaxes(String search, Pageable pageable) {
        return taxRepository.findTaxes(search, pageable).map(TaxEntity::toDto);
    }

    @Override
    public TaxDTO getTaxByUuid(UUID uuid) throws ImplException {
        return TaxEntity.toDto(taxRepository.findByUuidAndIsActiveTrue(uuid)
                .orElseThrow(() -> new ImplException(ResponseCode.NOT_FOUND,"Tax ID not found")));
    }

    @Override
    @Transactional
    public TaxDTO updateTax(UUID uuid, TaxDTO dto) throws ImplException {
        TaxEntity tax = taxRepository.findByUuidAndIsActiveTrue(uuid)
                .orElseThrow(() -> new ImplException(ResponseCode.NOT_FOUND,"Tax ID not found"));

        tax.setTaxId(dto.getTaxId());
        tax.setName(dto.getName());
        tax.setType(dto.getType());
        tax.setNpi(dto.getNpi());

        if (dto.getBillingAddress() != null)
            tax.setBillingAddress(AddressEntity.toEntity(dto.getBillingAddress()));

        return TaxEntity.toDto(taxRepository.save(tax));
    }

    @Override
    @Transactional
    public void deleteTax(UUID uuid) throws ImplException {
        TaxEntity tax = taxRepository.findByUuidAndIsActiveTrue(uuid)
                .orElseThrow(() -> new ImplException(ResponseCode.NOT_FOUND,"Tax ID not found"));

        tax.setIsActive(false);
        taxRepository.save(tax);
    }
}