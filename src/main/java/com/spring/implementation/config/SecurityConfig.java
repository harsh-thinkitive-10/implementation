package com.spring.implementation.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) {

        security.csrf(AbstractHttpConfigurer::disable).cors(cors -> {
                }).authorizeHttpRequests(auth -> auth

                        // ==================== PUBLIC ====================

                        .requestMatchers("/api/v1/auth/login", "/api/v1/auth/set-password", "/api/v1/auth/forgot-password", "/api/v1/auth/reset-password", "/api/v1/auth/refresh", "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()


                        // ==================== AUTH ====================

                        .requestMatchers("/api/v1/auth/logout", "/api/v1/auth/change-password").authenticated()


                        // ==================== TAX ====================

                        .requestMatchers(HttpMethod.GET, "/api/v1/tax").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/v1/tax").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/tax/{uuid}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/v1/tax/{uuid}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tax/{uuid}").hasRole("ADMIN")


                        // ==================== LOCATION ====================

                        .requestMatchers(HttpMethod.GET, "/api/v1/location").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/v1/location").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/location/{uuid}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/v1/location/{uuid}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/v1/location/{uuid}").hasRole("ADMIN")


                        // ==================== PATIENT ====================

                        .requestMatchers(HttpMethod.POST, "/api/v1/patient/register").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/patient/patients").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/patient/me").hasAnyRole("PATIENT", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/patient/dashboard").hasAnyRole("PATIENT", "ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/v1/patient/{uuid}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/v1/patient/{uuid}").hasRole("ADMIN")


                        // ==================== DOCTOR ====================

                        .requestMatchers(HttpMethod.POST, "/api/v1/doctor/register").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/doctor").hasAnyRole("DOCTOR", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/doctor/profile").hasAnyRole("DOCTOR", "ADMIN")

                        .requestMatchers(HttpMethod.PATCH, "/api/v1/doctor/profile").hasAnyRole("DOCTOR", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/doctor/dashboard").hasAnyRole("DOCTOR", "ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/v1/doctor/{uuid}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/v1/doctor/{uuid}").hasRole("ADMIN")


                        // ==================== ADMIN ====================

                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/profile").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/v1/admin/profile").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/dashboard").hasRole("ADMIN")


                        // ==================== PRESCRIPTION ====================

                        // ==================== PRESCRIPTION ====================

                        .requestMatchers(HttpMethod.POST, "/api/v1/prescription").hasRole("DOCTOR")

                        .requestMatchers(HttpMethod.GET, "/api/v1/prescription/{uuid}").hasAnyRole("DOCTOR", "PATIENT")

                        .requestMatchers(HttpMethod.GET, "/api/v1/prescription/appointment/{appointmentUuid}").hasAnyRole("DOCTOR", "PATIENT")


                        // ==================== APPOINTMENT ====================

                        .requestMatchers(HttpMethod.POST, "/api/v1/appointment").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PATCH, "/api/v1/appointment/{uuid}/status").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/appointment/patient").hasRole("PATIENT")

                        .requestMatchers(HttpMethod.GET, "/api/v1/appointment/doctor").hasRole("DOCTOR")

                        .requestMatchers(HttpMethod.GET, "/api/v1/appointment/admin").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/appointment/available-slots").hasRole("ADMIN")

                        // ALLERGY
                        .requestMatchers(HttpMethod.POST, "/api/v1/allergy").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/allergy/patient/{patientUuid}")
                        .hasAnyRole("PATIENT", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/allergy/{uuid}")
                        .hasAnyRole("PATIENT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/allergy/{uuid}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/allergy/{uuid}").hasRole("ADMIN")


                        // ==================== EVERYTHING ELSE ====================

                        .anyRequest().authenticated())

                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return security.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        return converter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:5174"));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public JwtDecoder jwtDecoder() {

        String issuer = "http://localhost:8081/realms/implementation";

        NimbusJwtDecoder decoder = NimbusJwtDecoder.withIssuerLocation(issuer).build();

        OAuth2TokenValidator<Jwt> issuerValidator = JwtValidators.createDefaultWithIssuer(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new JwtClaimValidator<List<String>>("aud", audience -> audience != null && audience.contains("implementation-app"));

        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(issuerValidator, audienceValidator));

        return decoder;
    }
}
