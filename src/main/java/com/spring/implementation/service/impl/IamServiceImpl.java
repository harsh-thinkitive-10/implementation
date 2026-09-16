package com.spring.implementation.service.impl;

import com.spring.implementation.dto.*;
import com.spring.implementation.exception.UserNameAlreadyExitsException;
import com.spring.implementation.service.IamService;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class IamServiceImpl implements IamService {

    private final Keycloak keycloak;
    private final WebClient.Builder webClientBuilder;
    private final KeycloakProperties keycloakProperties;

    @Override
    public String createUser(RegisterRequest request) {

        // =========================================================
        // 1. Create Keycloak User Representation
        // =========================================================
        UserRepresentation user = new UserRepresentation();

        user.setUsername(request.getEmail());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(true);
        user.setEmailVerified(true);

        // =========================================================
        // 2. Create User in Keycloak
        // =========================================================
        RealmResource realmResource =
                keycloak.realm(keycloakProperties.getRealm());

        UsersResource usersResource =
                realmResource.users();

        Response response =
                usersResource.create(user);

        try {

            if (response.getStatus() == 409) {
                throw new UserNameAlreadyExitsException(
                        request.getUsername() + " already exists"
                );
            }

            if (response.getStatus() != 201) {
                throw new RuntimeException(
                        "Failed to create Keycloak user. Status: "
                                + response.getStatus()
                );
            }

            // =====================================================
            // 3. Get Keycloak User ID
            // =====================================================
            String location =
                    response.getHeaderString("Location");

            if (location == null || location.isBlank()) {
                throw new RuntimeException(
                        "Keycloak user created but Location header is missing"
                );
            }

            String userId =
                    location.substring(
                            location.lastIndexOf("/") + 1
                    );

            // =====================================================
            // 4. Create Password Credential
            // =====================================================
            CredentialRepresentation credential =
                    new CredentialRepresentation();

            credential.setType(
                    CredentialRepresentation.PASSWORD
            );

            credential.setValue(
                    request.getPassword()
            );

            credential.setTemporary(true);

            // =====================================================
            // 5. Set Password
            // =====================================================
            keycloak
                    .realm(keycloakProperties.getRealm())
                    .users()
                    .get(userId)
                    .resetPassword(credential);

            // =====================================================
            // 6. Get Realm Role
            // =====================================================
            RoleRepresentation role =
                    keycloak
                            .realm(keycloakProperties.getRealm())
                            .roles()
                            .get(request.getRole())
                            .toRepresentation();

            // =====================================================
            // 7. Assign Realm Role
            // =====================================================
            keycloak
                    .realm(keycloakProperties.getRealm())
                    .users()
                    .get(userId)
                    .roles()
                    .realmLevel()
                    .add(List.of(role));

            // =====================================================
            // 8. Return Keycloak User ID
            // =====================================================
            log.info(
                    "Keycloak user created successfully. userId={}, role={}",
                    userId,
                    request.getRole()
            );

            return userId;

        } finally {
            response.close();
        }
    }

    @Override
    public LoginResponseDTO login(LoginDTO request) {

        String tokenUrl =
                keycloakProperties.getServerUrl()
                        + "/realms/"
                        + keycloakProperties.getRealm()
                        + "/protocol/openid-connect/token";

        log.info("Keycloak token URL: {}", tokenUrl);
        log.info("Keycloak login client: {}", keycloakProperties.getLoginClientId());
        log.info("Login username: {}", request.getUsername());

        return webClientBuilder
                .build()
                .post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(
                        BodyInserters.fromFormData(
                                        "grant_type", "password"
                                ).with(
                                        "client_id",
                                        keycloakProperties.getLoginClientId()
                                ).with(
                                        "username",
                                        request.getUsername()
                                ).with(
                                        "password",
                                        request.getPassword()
                                )
                )
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error(
                                            "Keycloak login failed. Status={}, Body={}",
                                            response.statusCode(),
                                            errorBody
                                    );

                                    return reactor.core.publisher.Mono.error(
                                            new RuntimeException(
                                                    "Keycloak login failed: "
                                                            + errorBody
                                            ));
                                })
                )
                .bodyToMono(LoginResponseDTO.class)
                .block();
    }

    @Override
    public void setPassword(String keycloakUserId, String newPassword) {

        CredentialRepresentation credential = new CredentialRepresentation();

        credential.setType(CredentialRepresentation.PASSWORD);

        credential.setValue(newPassword);

        credential.setTemporary(false);

        keycloak
                .realm(keycloakProperties.getRealm())
                .users()
                .get(keycloakUserId)
                .resetPassword(credential);
    }

    @Override
    public void changePassword(
            String keycloakUserId,
            String currentPassword,
            String newPassword
    ) {

        // 1. Get user from Keycloak
        UserRepresentation user =
                keycloak
                        .realm(keycloakProperties.getRealm())
                        .users()
                        .get(keycloakUserId)
                        .toRepresentation();

        String username = user.getUsername();

        if (username == null || username.isBlank()) {
            throw new RuntimeException(
                    "Username not found for Keycloak user: "
                            + keycloakUserId
            );
        }

        // 2. Verify current password
        verifyCurrentPassword(
                username,
                currentPassword
        );

        // 3. Create new password credential
        CredentialRepresentation credential =
                new CredentialRepresentation();

        credential.setType(
                CredentialRepresentation.PASSWORD
        );

        credential.setValue(newPassword);

        credential.setTemporary(false);

        // 4. Change password
        keycloak
                .realm(keycloakProperties.getRealm())
                .users()
                .get(keycloakUserId)
                .resetPassword(credential);

        log.info(
                "Password changed successfully. userId={}",
                keycloakUserId
        );
    }


    private void verifyCurrentPassword(
            String username,
            String currentPassword
    ) {

        String tokenUrl =
                keycloakProperties.getServerUrl()
                        + "/realms/"
                        + keycloakProperties.getRealm()
                        + "/protocol/openid-connect/token";

        try {

            webClientBuilder
                    .build()
                    .post()
                    .uri(tokenUrl)
                    .contentType(
                            MediaType.APPLICATION_FORM_URLENCODED
                    )
                    .body(
                            BodyInserters.fromFormData(
                                            "grant_type",
                                            "password"
                                    )
                                    .with(
                                            "client_id",
                                            keycloakProperties
                                                    .getLoginClientId()
                                    )
                                    .with(
                                            "username",
                                            username
                                    )
                                    .with(
                                            "password",
                                            currentPassword
                                    )
                    )
                    .retrieve()
                    .toBodilessEntity()
                    .block();

        } catch (Exception e) {

            throw new BadRequestException(
                    "Current password is incorrect"
            );
        }
    }

    @Override
    public KeycloakUser findUserByUsername(String username) {

        List<UserRepresentation> users =
                keycloak
                        .realm(keycloakProperties.getRealm())
                        .users()
                        .searchByUsername(
                                username,
                                true
                        );

        if (users.isEmpty()) {
            return null;
        }

        UserRepresentation user = users.get(0);

        return new KeycloakUser(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {

        String tokenUrl =
                keycloakProperties.getServerUrl()
                        + "/realms/"
                        + keycloakProperties.getRealm()
                        + "/protocol/openid-connect/token";

        log.info("Refreshing Keycloak access token");

        return webClientBuilder
                .build()
                .post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(
                        BodyInserters.fromFormData(
                                        "grant_type",
                                        "refresh_token"
                                )
                                .with(
                                        "client_id",
                                        keycloakProperties.getLoginClientId()
                                )
                                .with(
                                        "refresh_token",
                                        refreshToken
                                )
                )
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("Keycloak token refresh failed. Status={}, Body={}", response.statusCode(), errorBody);
                                    return reactor.core.publisher.Mono.error(new RuntimeException("Token refresh failed"));
                                })
                )
                .bodyToMono(TokenResponse.class)
                .block();
    }

    @Override
    public void logout(String refreshToken) {

        String logoutUrl =
                keycloakProperties.getServerUrl()
                        + "/realms/"
                        + keycloakProperties.getRealm()
                        + "/protocol/openid-connect/logout";

        log.info("Logging out from Keycloak");

        webClientBuilder
                .build()
                .post()
                .uri(logoutUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(
                        BodyInserters.fromFormData(
                                        "client_id",
                                        keycloakProperties.getLoginClientId()
                                )
                                .with(
                                        "refresh_token",
                                        refreshToken
                                )
                )
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> {

                                    log.error(
                                            "Keycloak logout failed. Status={}, Body={}",
                                            response.statusCode(),
                                            errorBody
                                    );

                                    return reactor.core.publisher.Mono.error(
                                            new RuntimeException(
                                                    "Logout failed"
                                            )
                                    );
                                })
                )
                .toBodilessEntity()
                .block();
    }

    @Override
    public void resetPassword(
            String keycloakUserId,
            String newPassword
    ) {

        CredentialRepresentation credential =
                new CredentialRepresentation();

        credential.setType(
                CredentialRepresentation.PASSWORD
        );

        credential.setValue(newPassword);

        credential.setTemporary(false);

        keycloak
                .realm(keycloakProperties.getRealm())
                .users()
                .get(keycloakUserId)
                .resetPassword(credential);
    }


    @Override
    public void updateUser(
            String keycloakUserId,
            String fullName,
            String email
    ) {

        UserRepresentation keycloakUser =
                keycloak
                        .realm(keycloakProperties.getRealm())
                        .users()
                        .get(keycloakUserId)
                        .toRepresentation();

        String[] nameParts =
                fullName
                        .trim()
                        .split("\\s+", 2);

        keycloakUser.setFirstName(nameParts[0]);

        keycloakUser.setLastName(
                nameParts.length > 1
                        ? nameParts[1]
                        : ""
        );

        keycloakUser.setEmail(email);

        keycloak
                .realm(keycloakProperties.getRealm())
                .users()
                .get(keycloakUserId)
                .update(keycloakUser);

        log.info(
                "Keycloak user updated successfully. userId={}",
                keycloakUserId
        );
    }

}

