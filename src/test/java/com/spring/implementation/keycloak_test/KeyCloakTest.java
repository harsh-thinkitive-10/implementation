package com.spring.implementation.keycloak_test;

import com.spring.implementation.service.KeycloakAdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class KeyCloakTest {

    @Autowired
    private KeycloakAdminService keycloakAdminService;

    @Test
    public void test(){
        keycloakAdminService.testConnection();
    }
}
