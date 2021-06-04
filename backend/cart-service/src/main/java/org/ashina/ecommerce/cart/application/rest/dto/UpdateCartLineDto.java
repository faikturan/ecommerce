package org.ashina.ecommerce.cart.application.rest.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateCartLineDto {

    @NotBlank
    private String productId;

    @NotNull
    private Integer quantity;
}
