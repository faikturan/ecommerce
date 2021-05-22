package org.ashina.ecommerce.uaa.server.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CreateAccountRequest {

    private String id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]{8,50}")
    private String password;

    private boolean admin;
}
