package org.ashina.ecommerce.cart.application.query.model;

import lombok.Data;
import org.ashina.ecommerce.sharedkernel.query.model.Query;

import javax.validation.constraints.NotBlank;

@Data
public class GetCartQuery extends Query {

    @NotBlank
    private String customerId;

}
