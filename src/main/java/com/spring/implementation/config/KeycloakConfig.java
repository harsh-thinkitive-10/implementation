package com.spring.implementation.config;

import com.spring.implementation.dto.KeycloakProperties;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableConfigurationProperties(KeycloakProperties.class)
public class KeycloakConfig {

    @Bean
    public Keycloak keycloak(KeycloakProperties properties){

        System.out.println("Server URL = " + properties.getServerUrl());

        System.out.println("Realm = " + properties.getRealm());

        System.out.println("Client ID = " + properties.getAdminClientId());

        System.out.println("Secret present = " + (properties.getAdminClientSecret() != null));

        System.out.println("Secret length = " + properties.getAdminClientSecret().length());

        return KeycloakBuilder.builder()
                .serverUrl(properties.getServerUrl())
                .realm(properties.getRealm())
                .clientId(properties.getAdminClientId())
                .clientSecret(properties.getAdminClientSecret())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }
}
