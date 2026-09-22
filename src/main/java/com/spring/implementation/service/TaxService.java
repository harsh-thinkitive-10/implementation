package com.spring.implementation.service;

import com.spring.implementation.dto.TaxDTO;
import com.spring.implementation.exception.ImplException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TaxService {

    TaxDTO createTax(TaxDTO dto);

    Page<TaxDTO> getAllTaxes(String search, Pageable pageable);

    TaxDTO getTaxByUuid(UUID uuid) throws ImplException;

    TaxDTO updateTax(UUID uuid, TaxDTO dto) throws ImplException;

    void deleteTax(UUID uuid) throws ImplException;
}