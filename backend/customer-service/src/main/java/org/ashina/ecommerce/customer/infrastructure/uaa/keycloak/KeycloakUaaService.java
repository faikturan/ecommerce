package org.ashina.ecommerce.customer.infrastructure.uaa.keycloak;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.ashina.ecommerce.customer.application.error.ErrorCode;
import org.ashina.ecommerce.customer.application.error.ServiceException;
import org.ashina.ecommerce.customer.domain.Customer;
import org.ashina.ecommerce.customer.infrastructure.uaa.UaaService;
import org.ashina.ecommerce.customer.infrastructure.uaa.keycloak.configuration.KeycloakProperties;
import org.ashina.ecommerce.sharedkernel.security.Role;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;

import javax.ws.rs.core.Response;
import java.util.Collections;

@RequiredArgsConstructor
@Slf4j
public class KeycloakUaaService implements UaaService {

    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;

    private static final String ATTRIBUTE_CUSTOMER_ID = "customerId";
    private static final String ATTRIBUTE_LAST_NAME = "lastName";
    private static final String ATTRIBUTE_FIRST_NAME = "firstName";

    @Override
    public void createAccount(Customer customer, String password) {
        RealmResource realmResource = keycloak.realm(keycloakProperties.getRealm());
        String createdUserId = "";
        try {
            // Create Keycloak user
            CredentialRepresentation credential = prepareCredential(password);
            UserRepresentation user = prepareUser(customer, credential);
            Response createUserResponse = realmResource.users().create(user);
            if (createUserResponse.getStatus() != HttpStatus.CREATED.value()) {
                throw new ServiceException(
                        ErrorCode.KEYCLOAK,
                        String.format("Create Keycloak user for email %s failed: %s %s",
                                customer.getEmail(), createUserResponse.getStatus(), createUserResponse.getStatusInfo()),
                        HttpStatus.INTERNAL_SERVER_ERROR
                );
            }

            // Get Keycloak user ID
            createdUserId = CreatedResponseUtil.getCreatedId(createUserResponse);
            log.debug("Created Keycloak user with ID {} for email {}", createdUserId, customer.getEmail());

            // Add role
            UserResource userResource = realmResource.users().get(createdUserId);
            addRole(realmResource, userResource);

            log.debug("Create Keycloak user for email {} done", customer.getEmail());
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            if (StringUtils.isNotBlank(createdUserId)) {
                realmResource.users().delete(createdUserId);
            }
            throw new ServiceException(
                    ErrorCode.KEYCLOAK,
                    String.format("Create Keycloak user %s failed: %s", customer.getEmail(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    private CredentialRepresentation prepareCredential(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        return credential;
    }

    private UserRepresentation prepareUser(Customer customer, CredentialRepresentation credential) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(customer.getEmail());
        user.setEmail(customer.getEmail());
        user.singleAttribute(ATTRIBUTE_CUSTOMER_ID, customer.getId());
        user.singleAttribute(ATTRIBUTE_LAST_NAME, customer.getLastName());
        user.singleAttribute(ATTRIBUTE_FIRST_NAME, customer.getFirstName());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        return user;
    }

    private void addRole(RealmResource realmResource, UserResource userResource) {
        // Get realm role ROLE_CUSTOMER
        RoleRepresentation role = realmResource.roles()
                .get(Role.ROLE_CUSTOMER.name())
                .toRepresentation();

        // Assign realm role ROLE_CUSTOMER to user
        userResource.roles()
                .realmLevel()
                .add(Collections.singletonList(role));
    }
}
