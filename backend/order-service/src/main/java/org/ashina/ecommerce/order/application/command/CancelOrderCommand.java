package org.ashina.ecommerce.order.application.command;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class CancelOrderCommand {

    @NotBlank
    private final String customerId;

    @NotBlank
    private final String orderId;
}
