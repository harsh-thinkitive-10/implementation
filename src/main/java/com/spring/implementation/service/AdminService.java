package com.spring.implementation.service;

import com.spring.implementation.dto.AdminProfileResponseDTO;
import com.spring.implementation.dto.AdminProfileUpdateRequestDTO;

public interface AdminService {

    AdminProfileResponseDTO getAdminProfile();

    AdminProfileResponseDTO updateAdminProfile(
            AdminProfileUpdateRequestDTO request
    );
}