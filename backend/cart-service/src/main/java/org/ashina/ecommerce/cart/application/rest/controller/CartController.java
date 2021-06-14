package org.ashina.ecommerce.cart.application.rest.controller;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.cart.application.command.AddProductCommand;
import org.ashina.ecommerce.cart.application.command.DeleteCartLineCommand;
import org.ashina.ecommerce.cart.application.command.UpdateCartLineCommand;
import org.ashina.ecommerce.cart.application.query.model.GetCartQuery;
import org.ashina.ecommerce.cart.application.query.model.GetCartView;
import org.ashina.ecommerce.cart.application.rest.dto.AddProductToCartDto;
import org.ashina.ecommerce.cart.application.rest.dto.GetCartDto;
import org.ashina.ecommerce.cart.application.rest.dto.UpdateCartLineDto;
import org.ashina.ecommerce.cart.application.rest.mapper.GetCartMapper;
import org.ashina.ecommerce.cart.infrastructure.security.SecurityContextHelper;
import org.ashina.ecommerce.sharedkernel.command.gateway.CommandGateway;
import org.ashina.ecommerce.sharedkernel.query.gateway.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;

    @GetMapping("/api/v1/carts")
    public ResponseEntity<GetCartDto> getCart(@AuthenticationPrincipal Jwt jwt) {
        GetCartQuery query = newGetCartQuery(jwt);
        GetCartView view = (GetCartView) queryGateway.execute(query);
        GetCartDto dto = GetCartMapper.INSTANCE.map(view);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    private GetCartQuery newGetCartQuery(Jwt jwt) {
        return GetCartQuery.builder()
                .customerId(SecurityContextHelper.currentCustomerId(jwt))
                .build();
    }

    @PostMapping("/api/v1/carts")
    public ResponseEntity<Void> addProduct(@Valid @RequestBody AddProductToCartDto dto,
                                           @AuthenticationPrincipal Jwt jwt) {
        AddProductCommand command = newAddProductCommand(dto, jwt);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private AddProductCommand newAddProductCommand(AddProductToCartDto dto, Jwt jwt) {
        return AddProductCommand.builder()
                .customerId(SecurityContextHelper.currentCustomerId(jwt))
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .build();
    }

    @PutMapping("/api/v1/carts")
    public ResponseEntity<Void> updateCartLine(@Valid @RequestBody UpdateCartLineDto dto,
                                           @AuthenticationPrincipal Jwt jwt) {
        UpdateCartLineCommand command = newUpdateCartLineCommand(dto, jwt);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private UpdateCartLineCommand newUpdateCartLineCommand(UpdateCartLineDto dto,
                                                           Jwt jwt) {
        return UpdateCartLineCommand.builder()
                .customerId(SecurityContextHelper.currentCustomerId(jwt))
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .build();
    }

    @DeleteMapping("/api/v1/carts")
    public ResponseEntity<Void> deleteCartLine(@RequestParam String productId) {
        DeleteCartLineCommand command = newDeleteCartLineCommand(productId);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private DeleteCartLineCommand newDeleteCartLineCommand(String productId) {
        return DeleteCartLineCommand.builder()
                .productId(productId)
                .build();
    }
}
