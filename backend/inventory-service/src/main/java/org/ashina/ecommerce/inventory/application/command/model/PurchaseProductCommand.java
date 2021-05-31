package org.ashina.ecommerce.inventory.application.command.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class PurchaseProductCommand {

    @NotBlank
    private String productId;

    @NotNull
    @Positive
    private Integer quantity;
}
