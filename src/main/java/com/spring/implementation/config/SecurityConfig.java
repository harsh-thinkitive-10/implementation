package com.spring.implementation.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity security
    ) {

        security
                .csrf(AbstractHttpConfigurer::disable)

                .cors(cors -> {})

                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(
                                        "/api/v1/auth/forgot-password",
                                        "/api/v1/auth/reset-password",
                                        "/api/v1/auth/refresh",
                                        "/api/v1/auth/logout"
                                ).permitAll()
                                .requestMatchers(
                                        "/api/v1/auth/change-password"
                                ).authenticated()
                                .requestMatchers(
                                        "/api/v1/auth/**"
                                ).permitAll()
                                .requestMatchers(
                                        "/api/v1/public/**"
                                ).permitAll()
                                .requestMatchers(
                                        "/api/v1/admin/**",
                                        "/api/v1/appointment"
                                ).hasRole("ADMIN")
                                .requestMatchers(
                                        "/api/v1/patient/**"
                                ).hasAnyRole("PATIENT", "ADMIN")
                                .requestMatchers(
                                        "/api/v1/doctor/**"
                                ).hasAnyRole("DOCTOR", "ADMIN")
                                .anyRequest()
                                .authenticated()
                )

                .oauth2ResourceServer(
                        oauth2 -> oauth2.jwt(
                                jwt -> jwt
                                        .jwtAuthenticationConverter(
                                                jwtAuthenticationConverter()
                                        )
                        )
                );

        return security.build();
    }

    @Bean
    public JwtAuthenticationConverter
    jwtAuthenticationConverter() {

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(
                new KeycloakRoleConverter()
        );

        return converter;
    }

    @Bean
    public CorsConfigurationSource
    corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of(
                        "http://localhost:5173",
                        "http://localhost:5174"
                )
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "PATCH",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of(
                        "Authorization",
                        "Content-Type"
                )
        );

        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}