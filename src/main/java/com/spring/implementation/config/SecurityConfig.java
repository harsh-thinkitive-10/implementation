package com.spring.implementation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security){
        security.csrf(
                AbstractHttpConfigurer::disable
        ).authorizeHttpRequests(
                auth->auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/public/**").permitAll()
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/patient/**").hasAnyRole("PATIENT","ADMIN")
                        .requestMatchers("/api/v1/doctor/**").hasAnyRole("DOCTOR","ADMIN")

                        .anyRequest().authenticated()
        ).oauth2ResourceServer(
                oauth2
                        ->oauth2.jwt(
                                jwt-> jwt.jwtAuthenticationConverter(
                                        jwtAuthenticationConverter()
                                )
                        )
        );
        return security.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        return jwtAuthenticationConverter;
    }


}
