package org.ashina.ecommerce.cart.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddProductToCartCommand {

    @NotBlank
    private String customerId;

    @NotBlank
    private String productId;

    @NotBlank
    private Integer quantity;
}
