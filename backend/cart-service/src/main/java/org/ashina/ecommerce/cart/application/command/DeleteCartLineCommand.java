package org.ashina.ecommerce.cart.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeleteCartLineCommand {

    @NotBlank
    private String customerId;

    @NotBlank
    private String productId;
}
