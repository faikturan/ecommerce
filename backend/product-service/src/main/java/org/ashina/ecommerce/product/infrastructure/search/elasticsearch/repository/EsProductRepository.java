package org.ashina.ecommerce.product.infrastructure.search.elasticsearch.repository;

import org.ashina.ecommerce.product.infrastructure.search.elasticsearch.model.EsProduct;
import org.ashina.ecommerce.product.infrastructure.search.elasticsearch.repository.custom.CustomEsProductRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsProductRepository extends ElasticsearchRepository<EsProduct, String>, CustomEsProductRepository {
}
