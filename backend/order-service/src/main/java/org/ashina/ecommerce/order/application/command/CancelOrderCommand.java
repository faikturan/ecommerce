package org.ashina.ecommerce.order.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CancelOrderCommand {

    @NotBlank
    private String customerId;

    @NotBlank
    private String orderId;
}
