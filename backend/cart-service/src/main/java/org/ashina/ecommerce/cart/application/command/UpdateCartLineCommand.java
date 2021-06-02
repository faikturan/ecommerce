package org.ashina.ecommerce.cart.application.command;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class UpdateCartLineCommand {

    @NotBlank
    private final String customerId;

    @NotBlank
    private final String productId;

    @NotNull
    private final Integer quantity;
}
