package org.ashina.ecommerce.cart.application.command;

import lombok.Data;
import org.ashina.ecommerce.sharedkernel.command.model.Command;

import javax.validation.constraints.NotBlank;

@Data
public class DeleteCartLineCommand extends Command {

    @NotBlank
    private String customerId;

    @NotBlank
    private String productId;
}
