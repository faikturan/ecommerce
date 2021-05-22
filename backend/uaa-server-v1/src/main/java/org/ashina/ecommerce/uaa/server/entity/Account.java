package org.ashina.ecommerce.uaa.server.entity;

import lombok.Data;
import org.ashina.ecommerce.sharedkernel.security.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "accounts")
@Data
public class Account {

    @Id
    private String id;

    private String email;

    private String password;

    private Set<Role> roles;
}
