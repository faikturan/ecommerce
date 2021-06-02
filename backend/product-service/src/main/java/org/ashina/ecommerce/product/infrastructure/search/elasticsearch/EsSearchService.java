package org.ashina.ecommerce.product.infrastructure.search.elasticsearch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.product.domain.Product;
import org.ashina.ecommerce.product.infrastructure.search.SearchService;
import org.ashina.ecommerce.product.infrastructure.search.elasticsearch.model.EsProduct;
import org.ashina.ecommerce.product.infrastructure.search.elasticsearch.repository.EsProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
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
        log.debug("Create product document {} done", product.getId());
    }

    private EsProduct newElasticsearchProduct(Product product) {
        EsProduct esProduct = new EsProduct();
        esProduct.setId(product.getId());
        esProduct.setName(product.getName());
        esProduct.setDescription(product.getDescription());
        return esProduct;
    }

    @Override
    @Async("elasticsearchIndexingTaskExecutor")
    public void delete(String productId) {
        repository.deleteById(productId);
        log.debug("Delete product document {} done", productId);
    }
}
