package org.ashina.ecommerce.catalog.application.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.catalog.application.command.model.CreateProductCommand;
import org.ashina.ecommerce.catalog.application.query.model.GetProductsQuery;
import org.ashina.ecommerce.catalog.application.query.model.GetProductsView;
import org.ashina.ecommerce.catalog.application.query.model.SearchProductQuery;
import org.ashina.ecommerce.catalog.application.query.model.SearchProductView;
import org.ashina.ecommerce.catalog.application.rest.dto.CreateProductDto;
import org.ashina.ecommerce.sharedkernel.command.gateway.CommandGateway;
import org.ashina.ecommerce.sharedkernel.query.gateway.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @GetMapping("/api/v1/products")
    public ResponseEntity<GetProductsView> getProducts(@RequestParam Collection<String> ids) {
        GetProductsQuery query = newGetProductsQuery(ids);
        GetProductsView view = (GetProductsView) queryGateway.execute(query);
        return new ResponseEntity<>(view, HttpStatus.OK);
    }

    private GetProductsQuery newGetProductsQuery(Collection<String> ids) {
        GetProductsQuery query = new GetProductsQuery();
        query.setIds(ids);
        return query;
    }

    @GetMapping("/api/v1/products/search")
    public ResponseEntity<SearchProductView> searchProduct(@RequestParam String keyword,
                                                           @RequestParam(required = false, defaultValue = "0") int page,
                                                           @RequestParam(required = false, defaultValue = "20") int size) {
        SearchProductQuery query = newSearchProductQuery(keyword, page, size);
        SearchProductView view = (SearchProductView) queryGateway.execute(query);
        return new ResponseEntity<>(view, HttpStatus.OK);
    }

    private SearchProductQuery newSearchProductQuery(String keyword, int page, int size) {
        SearchProductQuery query = new SearchProductQuery();
        query.setKeyword(keyword);
        query.setPage(page);
        query.setSize(size);
        query.setHasValidate(true);
        return query;
    }

    @PostMapping("/api/v1/products")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody CreateProductDto dto) {
        CreateProductCommand command = newCreateProductCommand(dto);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private CreateProductCommand newCreateProductCommand(CreateProductDto dto) {
        CreateProductCommand command = new CreateProductCommand();
        command.setName(dto.getName());
        command.setDescription(dto.getDescription());
        command.setImage(dto.getImage());
        command.setPrice(dto.getPrice());
        return command;
    }

}
