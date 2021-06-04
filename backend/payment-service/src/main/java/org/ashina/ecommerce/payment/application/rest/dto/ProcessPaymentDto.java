package org.ashina.ecommerce.payment.application.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ProcessPaymentDto {

    @NotBlank
    private String orderId;

    @NotNull
    @Positive
    private Integer amount;
}
