package org.ashina.ecommerce.product.application.command.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Builder
public class PurchaseProductCommand {

    @NotBlank
    private final String productId;

    @NotNull
    @Positive
    private final Integer quantity;
}
