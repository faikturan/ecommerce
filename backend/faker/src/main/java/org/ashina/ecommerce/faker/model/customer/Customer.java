package org.ashina.ecommerce.faker.model.customer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "customers")
@Getter
@Setter
public class Customer extends BaseEntity {

    private String lastName;

    private String firstName;

    private Gender gender;

    private Date dateOfBirth;

    private int orders;
}
