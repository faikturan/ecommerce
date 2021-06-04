package org.ashina.ecommerce.product.application.rest.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class PurchaseProductDto {

    @NotBlank
    private String productId;

    @NotNull
    @Positive
    private Integer quantity;
}
