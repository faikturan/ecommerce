package org.ashina.ecommerce.customer.application.command.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
public class CreateCustomerCommand {

    @NotBlank
    @Size(max = 100)
    private final String fullName;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    @Size(min = 8, max = 50)
    private final String password;
}
