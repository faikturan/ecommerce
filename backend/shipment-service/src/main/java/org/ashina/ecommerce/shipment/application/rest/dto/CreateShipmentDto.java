package org.ashina.ecommerce.shipment.application.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateShipmentDto {

    @NotBlank
    private String orderId;

    @NotBlank
    private String fullName;

    @NotBlank
    private String phoneNo;

    @NotBlank
    private String address;
}
