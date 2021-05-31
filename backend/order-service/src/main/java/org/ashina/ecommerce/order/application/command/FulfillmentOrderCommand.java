package org.ashina.ecommerce.order.application.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FulfillmentOrderCommand {

    @NotBlank
    private String customerId;
}
