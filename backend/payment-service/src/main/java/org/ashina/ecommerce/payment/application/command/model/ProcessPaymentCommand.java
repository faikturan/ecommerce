package org.ashina.ecommerce.payment.application.command.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Builder
public class ProcessPaymentCommand {

    @NotBlank
    private final String customerId;

    @NotBlank
    private final String orderId;

    @NotNull
    @Positive
    private final Integer amount;
}
