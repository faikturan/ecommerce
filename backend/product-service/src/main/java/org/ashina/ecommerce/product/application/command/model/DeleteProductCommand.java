package org.ashina.ecommerce.product.application.command.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class DeleteProductCommand {

    @NotBlank
    private final String productId;
}
