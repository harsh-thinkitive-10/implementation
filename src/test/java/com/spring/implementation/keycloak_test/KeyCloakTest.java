package com.spring.implementation.keycloak_test;

import com.spring.implementation.service.IamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class KeyCloakTest {

    @Autowired
    private IamService iamService;

    @Test
    public void test(){
        iamService.testConnection();
    }
}
