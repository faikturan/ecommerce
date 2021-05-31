package org.ashina.ecommerce.catalog.application.query.model;

import lombok.Data;

import java.util.Collection;

@Data
public class GetProductsQuery {

    private Collection<String> ids;
}
