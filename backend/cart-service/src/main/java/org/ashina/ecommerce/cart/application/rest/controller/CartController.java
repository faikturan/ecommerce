package org.ashina.ecommerce.cart.application.rest.controller;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.cart.application.command.AddProductToCartCommand;
import org.ashina.ecommerce.cart.application.command.DeleteCartLineCommand;
import org.ashina.ecommerce.cart.application.command.UpdateCartLineCommand;
import org.ashina.ecommerce.cart.application.query.model.GetCartQuery;
import org.ashina.ecommerce.cart.application.query.model.GetCartView;
import org.ashina.ecommerce.cart.application.rest.dto.AddProductToCartDto;
import org.ashina.ecommerce.cart.application.rest.dto.UpdateCartLineDto;
import org.ashina.ecommerce.cart.infrastructure.security.SecurityContextHelper;
import org.ashina.ecommerce.sharedkernel.command.gateway.CommandGateway;
import org.ashina.ecommerce.sharedkernel.query.gateway.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final QueryGateway queryGateway;
    private final CommandGateway commandGateway;

    @GetMapping("/api/v1/carts")
    public ResponseEntity<GetCartView> getCart(@AuthenticationPrincipal Jwt jwt) {
        GetCartQuery query = newGetCartQuery(jwt);
        GetCartView view = (GetCartView) queryGateway.execute(query);
        return new ResponseEntity<>(view, HttpStatus.OK);
    }

    private GetCartQuery newGetCartQuery(Jwt jwt) {
        GetCartQuery query = new GetCartQuery();
        query.setCustomerId(SecurityContextHelper.currentCustomerId(jwt));
        query.setHasValidate(true);
        return query;
    }

    @PostMapping("/api/v1/carts/add-product")
    public ResponseEntity<Void> addProduct(@Valid @RequestBody AddProductToCartDto dto,
                                           @AuthenticationPrincipal Jwt jwt) {
        AddProductToCartCommand command = newAddProductToCartCommand(dto, jwt);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private AddProductToCartCommand newAddProductToCartCommand(AddProductToCartDto dto, Jwt jwt) {
        AddProductToCartCommand command = new AddProductToCartCommand();
        command.setCustomerId(SecurityContextHelper.currentCustomerId(jwt));
        command.setProductId(dto.getProductId());
        command.setQuantity(dto.getQuantity());
        return command;
    }

    @PutMapping("/api/v1/carts/lines")
    public ResponseEntity<Void> updateLine(@Valid @RequestBody UpdateCartLineDto dto,
                                           @AuthenticationPrincipal Jwt jwt) {
        UpdateCartLineCommand command = newUpdateCartLineCommand(dto, jwt);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private UpdateCartLineCommand newUpdateCartLineCommand(UpdateCartLineDto dto,
                                                           Jwt jwt) {
        UpdateCartLineCommand command = new UpdateCartLineCommand();
        command.setCustomerId(SecurityContextHelper.currentCustomerId(jwt));
        command.setProductId(dto.getProductId());
        command.setQuantity(dto.getQuantity());
        return command;
    }

    @DeleteMapping("/api/v1/carts/lines/{productId}")
    public ResponseEntity<Void> deleteLine(@PathVariable String productId) {
        DeleteCartLineCommand command = newDeleteCartLineCommand(productId);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private DeleteCartLineCommand newDeleteCartLineCommand(String productId) {
        DeleteCartLineCommand command = new DeleteCartLineCommand();
        command.setProductId(productId);
        return command;
    }

}
