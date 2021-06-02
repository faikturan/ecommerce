package org.ashina.ecommerce.product.application.query.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;

@Getter
@Builder
public class SearchProductsQuery {

    private final String keyword;

    @Min(0)
    private final Integer page;

    @Min(1)
    private final Integer size;
}
