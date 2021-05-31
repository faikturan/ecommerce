package org.ashina.ecommerce.cart.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCartLineCommand {

    @NotBlank
    private String customerId;

    @NotBlank
    private String productId;

    @NotNull
    private Integer quantity;
}
