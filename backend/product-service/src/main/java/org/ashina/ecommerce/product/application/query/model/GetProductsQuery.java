package org.ashina.ecommerce.product.application.query.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Getter
@Builder
public class GetProductsQuery {

    @NotEmpty
    private final Collection<String> ids;
}
