package org.ashina.ecommerce.cart.application.command;

import lombok.Data;
import org.ashina.ecommerce.sharedkernel.command.model.Command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCartLineCommand extends Command {

    @NotBlank
    private String customerId;

    @NotBlank
    private String productId;

    @NotNull
    private Integer quantity;
}
