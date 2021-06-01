package org.ashina.ecommerce.catalog.infrastructure.search.elasticsearch;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.catalog.domain.Product;
import org.ashina.ecommerce.catalog.infrastructure.search.SearchService;
import org.ashina.ecommerce.catalog.infrastructure.search.elasticsearch.model.EsProduct;
import org.ashina.ecommerce.catalog.infrastructure.search.elasticsearch.repository.EsProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EsSearchService implements SearchService {

    private final EsProductRepository repository;

    @Override
    public List<String> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<EsProduct> results = repository.search(keyword, pageable);
        return results
                .stream()
                .map(EsProduct::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Async("elasticsearchIndexingTaskExecutor")
    public void save(Product product) {
        EsProduct esProduct = newElasticsearchProduct(product);
        repository.save(esProduct);
    }

    private EsProduct newElasticsearchProduct(Product product) {
        EsProduct esProduct = new EsProduct();
        esProduct.setId(product.getId());
        esProduct.setName(product.getName());
        esProduct.setDescription(product.getDescription());
        return esProduct;
    }
}
