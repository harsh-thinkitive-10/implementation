package com.spring.implementation.controller;

import com.spring.implementation.dto.AdminProfileResponseDTO;
import com.spring.implementation.dto.AdminProfileUpdateRequestDTO;
import com.spring.implementation.dto.Response;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController extends AppController {

    private final AdminService adminService;

    @GetMapping("/profile")
    public ResponseEntity<Response> getAdminProfile() {
        AdminProfileResponseDTO response = adminService.getAdminProfile();
        return data(ResponseCode.OK, "Admin profile fetched successfully", response);
    }

    @PutMapping("/profile")
    public ResponseEntity<Response> updateAdminProfile(@Valid @RequestBody AdminProfileUpdateRequestDTO request) {
        AdminProfileResponseDTO response = adminService.updateAdminProfile(request);

        return data(ResponseCode.OK, "Admin profile updated successfully", response);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Response> getDashboard() {
        return data(ResponseCode.OK, "Admin dashboard fetched successfully", adminService.getDashboard());
    }
}