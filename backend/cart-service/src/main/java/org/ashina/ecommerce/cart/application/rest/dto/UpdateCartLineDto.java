package org.ashina.ecommerce.cart.application.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCartLineDto {

    @NotBlank
    private String productId;

    @NotNull
    private Integer quantity;
}
