package org.ashina.ecommerce.order.infrastructure.ecommerce.feign.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class ProcessPaymentDto {

    @NotBlank
    private String customerId;

    @NotBlank
    private String orderId;

    @NotNull
    @Positive
    private Integer amount;
}
