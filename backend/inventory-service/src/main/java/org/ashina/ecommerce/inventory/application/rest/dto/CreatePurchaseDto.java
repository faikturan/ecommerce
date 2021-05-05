package org.ashina.ecommerce.inventory.application.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CreatePurchaseDto {

    @NotBlank
    private String supplierId;

    @NotBlank
    private String productId;

    @NotNull
    @Positive
    private Integer quantity;
}
