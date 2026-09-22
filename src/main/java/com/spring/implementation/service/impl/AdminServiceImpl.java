package com.spring.implementation.service.impl;

import com.spring.implementation.dto.AdminDashboardDTO;
import com.spring.implementation.dto.AdminProfileResponseDTO;
import com.spring.implementation.dto.AdminProfileUpdateRequestDTO;
import com.spring.implementation.entity.AdminEntity;
import com.spring.implementation.repository.AdminRepository;
import com.spring.implementation.service.AdminService;
import com.spring.implementation.service.IamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final IamService iamService;

    @Override
    @Transactional(readOnly = true)
    public AdminProfileResponseDTO getAdminProfile() {

        String keycloakUserId = getAuthenticatedUserId();

        AdminEntity admin = adminRepository.findByKeycloakUserId(keycloakUserId).orElseThrow(() -> new RuntimeException("Admin not found"));

        return AdminProfileResponseDTO.builder()
                .uuid(admin.getUuid())
                .fullName(admin.getFullName())
                .email(admin.getEmail())
                .build();
    }

    @Override
    @Transactional
    public AdminProfileResponseDTO updateAdminProfile(AdminProfileUpdateRequestDTO request) {

        String keycloakUserId = getAuthenticatedUserId();

        AdminEntity admin = adminRepository.findByKeycloakUserId(keycloakUserId).orElseThrow(() -> new RuntimeException("Admin not found"));

        /*
         * Update DB
         */
        admin.setFullName(request.getFullName());
        admin.setEmail(request.getEmail());

        AdminEntity updatedAdmin = adminRepository.save(admin);

        /*
         * Update Keycloak
         */
        iamService.updateUser(keycloakUserId, request.getFullName(), request.getEmail());

        return AdminProfileResponseDTO.builder()
                .uuid(updatedAdmin.getUuid())
                .fullName(updatedAdmin.getFullName())
                .email(updatedAdmin.getEmail())
                .build();
    }

    @Override
    public AdminDashboardDTO getDashboard() {
        return AdminDashboardDTO.builder()
                .totalPatients(adminRepository.countPatients())
                .totalDoctors(adminRepository.countDoctors())
                .totalLocations(adminRepository.countLocations())
                .totalAppointments(adminRepository.countAppointments())
                .todayAppointments(adminRepository.countTodayAppointments())
                .scheduledAppointments(adminRepository.countScheduledAppointments())
                .completedAppointments(adminRepository.countCompletedAppointments())
                .cancelledAppointments(adminRepository.countCancelledAppointments())
                .build();
    }

    private String getAuthenticatedUserId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }

        return authentication.getName();
    }


}