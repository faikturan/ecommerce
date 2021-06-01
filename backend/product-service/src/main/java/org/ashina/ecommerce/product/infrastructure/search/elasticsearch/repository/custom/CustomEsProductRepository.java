package org.ashina.ecommerce.product.infrastructure.search.elasticsearch.repository.custom;

import org.ashina.ecommerce.product.infrastructure.search.elasticsearch.model.EsProduct;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomEsProductRepository {

    List<EsProduct> search(String keyword, Pageable pageable);
}
