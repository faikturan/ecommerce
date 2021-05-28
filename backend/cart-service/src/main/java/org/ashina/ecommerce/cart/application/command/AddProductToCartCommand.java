package org.ashina.ecommerce.cart.application.command;

import lombok.Data;
import org.ashina.ecommerce.sharedkernel.command.model.Command;

import javax.validation.constraints.NotBlank;

@Data
public class AddProductToCartCommand extends Command {

    @NotBlank
    private String customerId;

    @NotBlank
    private String productId;

    @NotBlank
    private Integer quantity;
}
