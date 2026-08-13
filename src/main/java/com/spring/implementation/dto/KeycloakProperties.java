package com.spring.implementation.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {


    private String serverUrl;
    private String realm;
    private String adminClientId;
    private String adminClientSecret;
    private String loginClientId;
}