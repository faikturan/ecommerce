package org.ashina.ecommerce.customer.infrastructure.uaa.spring.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.customer.domain.Customer;
import org.ashina.ecommerce.customer.infrastructure.uaa.UaaService;
import org.ashina.ecommerce.customer.infrastructure.uaa.spring.model.CreateAccountRequest;
import org.ashina.ecommerce.sharedkernel.exception.InfrastructureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
public class SpringUaaService implements UaaService {

    private final RestTemplate restTemplate;

    @Value("${uaa.create-account-endpoint.internal}")
    private String createAccountEndpoint;

    @Override
    public void createAccount(Customer customer, String password) {
        CreateAccountRequest request = new CreateAccountRequest(customer, password);
        try {
            restTemplate.postForObject(createAccountEndpoint, request, Void.class);
        } catch (RestClientResponseException e) {
            throw new InfrastructureException(String.format("Create account failed: %s", e.getResponseBodyAsString()));
        }
    }
}
