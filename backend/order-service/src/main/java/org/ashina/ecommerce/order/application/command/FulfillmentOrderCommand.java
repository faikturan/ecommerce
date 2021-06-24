package org.ashina.ecommerce.order.application.command;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class FulfillmentOrderCommand {

    @NotBlank
    private final String customerId;

    @NotBlank
    private final String name;

    @NotBlank
    private final String phoneNumber;

    @NotBlank
    private final String address;
}
