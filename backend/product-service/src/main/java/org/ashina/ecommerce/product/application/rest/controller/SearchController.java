package org.ashina.ecommerce.product.application.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.product.application.query.model.SearchProductsQuery;
import org.ashina.ecommerce.product.application.query.model.SearchProductsView;
import org.ashina.ecommerce.product.application.rest.dto.SearchProductsDto;
import org.ashina.ecommerce.product.application.rest.mapper.SearchProductsMapper;
import org.ashina.ecommerce.sharedkernel.query.gateway.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final QueryGateway queryGateway;

    @GetMapping("/api/v1/search")
    public ResponseEntity<SearchProductsDto> searchProducts(@RequestParam(required = false) String keyword,
                                                            @RequestParam(required = false, defaultValue = "0") int page,
                                                            @RequestParam(required = false, defaultValue = "20") int size) {
        SearchProductsQuery query = newSearchProductsQuery(keyword, page, size);
        SearchProductsView view = (SearchProductsView) queryGateway.execute(query, true);
        SearchProductsDto dto = SearchProductsMapper.INSTANCE.map(view);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    private SearchProductsQuery newSearchProductsQuery(String keyword, int page, int size) {
        return SearchProductsQuery.builder()
                .keyword(keyword)
                .page(page)
                .size(size)
                .build();
    }
}
