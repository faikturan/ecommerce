package org.ashina.ecommerce.cart.application.query.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class GetCartQuery {

    @NotBlank
    private final String customerId;
}
