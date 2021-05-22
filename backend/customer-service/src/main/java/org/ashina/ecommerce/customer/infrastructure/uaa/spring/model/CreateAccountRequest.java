package org.ashina.ecommerce.customer.infrastructure.uaa.spring.model;

import lombok.Data;
import org.ashina.ecommerce.customer.domain.Customer;

@Data
public class CreateAccountRequest {

    private String id;

    private String email;

    private String password;

    private boolean admin;

    public CreateAccountRequest(Customer customer, String password) {
        this.id = customer.getId();
        this.email = customer.getEmail();
        this.password = password;
        this.admin = false;
    }
}
