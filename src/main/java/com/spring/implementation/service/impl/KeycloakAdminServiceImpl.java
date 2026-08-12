package com.spring.implementation.service.impl;

import com.spring.implementation.dto.RegisterPatient;
import com.spring.implementation.dto.RegisterRequest;
import com.spring.implementation.service.KeycloakAdminService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakAdminServiceImpl implements KeycloakAdminService {

    private final Keycloak keycloak;


    @Override
    public void testConnection() {


        int count = keycloak
                .realm("implementation")
                .users()
                .count();

        System.out.println("Keycloak users = " + count);

        keycloak.realm("implementation").toRepresentation();
        log.info("successfully connected to keycloak admin.");

    }

    @Override
    public String createUser(RegisterRequest request) {

        // 1. Create Keycloak user representation
        UserRepresentation user = new UserRepresentation();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(true);
        user.setEmailVerified(true);


        // 2. Create user in Keycloak
        Response response = keycloak
                .realm("implementation")
                .users()
                .create(user);

        if (response.getStatus() != 201) {
            int status = response.getStatus();
            response.close();

            throw new RuntimeException(
                    "Failed to create Keycloak user. Status: " + status
            );
        }


        // 3. Get Keycloak user ID
        String location = response.getHeaderString("Location");

        String userId = location.substring(
                location.lastIndexOf("/") + 1
        );

        response.close();


        // 4. Create password credential
        CredentialRepresentation credential =
                new CredentialRepresentation();

        credential.setType(
                CredentialRepresentation.PASSWORD
        );

        credential.setValue(request.getPassword());

        credential.setTemporary(false);


        // 5. Set password
        keycloak
                .realm("implementation")
                .users()
                .get(userId)
                .resetPassword(credential);


        // 6. Get requested role
        RoleRepresentation role = keycloak
                .realm("implementation")
                .roles()
                .get(request.getRole())
                .toRepresentation();


        // 7. Assign role
        keycloak
                .realm("implementation")
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(List.of(role));


        // 8. Return Keycloak user ID
        return userId;
    }
}
