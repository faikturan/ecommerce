package org.ashina.ecommerce.catalog.application.query.model;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class SearchProductQuery {

    private String keyword;

    @Min(0)
    private Integer page;

    @Min(1)
    private Integer size;
}
