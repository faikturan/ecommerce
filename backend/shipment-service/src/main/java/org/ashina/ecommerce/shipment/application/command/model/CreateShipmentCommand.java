package org.ashina.ecommerce.shipment.application.command.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CreateShipmentCommand {

    @NotBlank
    private String orderId;

    @NotBlank
    private String fullName;

    @NotBlank
    private String phoneNo;

    @NotBlank
    private String address;
}
