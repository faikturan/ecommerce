package org.ashina.ecommerce.cart.application.query.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GetCartQuery {

    @NotBlank
    private String customerId;
}
