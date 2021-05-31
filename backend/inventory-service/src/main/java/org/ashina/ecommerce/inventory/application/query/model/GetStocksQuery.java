package org.ashina.ecommerce.inventory.application.query.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class GetStocksQuery {

    @NotEmpty
    private List<String> productIds;
}
