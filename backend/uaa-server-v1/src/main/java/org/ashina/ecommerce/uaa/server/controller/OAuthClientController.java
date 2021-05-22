package org.ashina.ecommerce.uaa.server.controller;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.uaa.server.dto.request.CreateOAuthClientRequest;
import org.ashina.ecommerce.uaa.server.dto.response.CreateOAuthClientResponse;
import org.ashina.ecommerce.uaa.server.service.OAuthClientService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OAuthClientController {

    // Dependencies
    // -----------------------------------------------------------------------------------------------------------------

    private final OAuthClientService oAuthClientService;

    // APIs
    // -----------------------------------------------------------------------------------------------------------------

    @PostMapping("/api/v1/oauth-clients")
    public CreateOAuthClientResponse createOAuthClient(@Valid @RequestBody CreateOAuthClientRequest request) {
        return oAuthClientService.createOAuthClient(request);
    }

    @DeleteMapping("/api/v1/oauth-clients/{id}")
    public void deleteOAuthClient(@PathVariable String id) {
        oAuthClientService.deleteClient(id);
    }
}
