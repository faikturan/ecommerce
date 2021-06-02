package org.ashina.ecommerce.product.application.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.product.application.command.model.CreateProductCommand;
import org.ashina.ecommerce.product.application.command.model.DeleteProductCommand;
import org.ashina.ecommerce.product.application.command.model.PurchaseProductCommand;
import org.ashina.ecommerce.product.application.query.model.GetProductsQuery;
import org.ashina.ecommerce.product.application.query.model.GetProductsView;
import org.ashina.ecommerce.product.application.query.model.SearchProductsQuery;
import org.ashina.ecommerce.product.application.query.model.SearchProductsView;
import org.ashina.ecommerce.product.application.rest.dto.CreateProductDto;
import org.ashina.ecommerce.product.application.rest.dto.GetProductsDto;
import org.ashina.ecommerce.product.application.rest.dto.PurchaseProductDto;
import org.ashina.ecommerce.product.application.rest.dto.SearchProductsDto;
import org.ashina.ecommerce.product.application.rest.mapper.GetProductsMapper;
import org.ashina.ecommerce.product.application.rest.mapper.SearchProductsMapper;
import org.ashina.ecommerce.sharedkernel.command.gateway.DefaultCommandGateway;
import org.ashina.ecommerce.sharedkernel.query.gateway.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private final DefaultCommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @GetMapping(value = "/api/v1/products", params = "action=get")
    public ResponseEntity<GetProductsDto> getProducts(@RequestParam Collection<String> ids) {
        GetProductsQuery query = newGetProductsQuery(ids);
        GetProductsView view = (GetProductsView) queryGateway.execute(query, true);
        GetProductsDto dto = GetProductsMapper.INSTANCE.map(view);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    private GetProductsQuery newGetProductsQuery(Collection<String> ids) {
        return GetProductsQuery.builder()
                .ids(ids)
                .build();
    }

    @GetMapping(value = "/api/v1/products", params = "action=search")
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

    @PostMapping(value = "/api/v1/products", params = "action=create")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody CreateProductDto dto) {
        CreateProductCommand command = newCreateProductCommand(dto);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private CreateProductCommand newCreateProductCommand(CreateProductDto dto) {
        return CreateProductCommand.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .image(dto.getImage())
                .price(dto.getPrice())
                .attributes(dto.getAttributes())
                .build();
    }

    @PostMapping(value = "/api/v1/products", params = "action=purchase")
    public ResponseEntity<Void> purchaseProduct(@Valid @RequestBody PurchaseProductDto dto) {
        PurchaseProductCommand command = newPurchaseProductCommand(dto);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private PurchaseProductCommand newPurchaseProductCommand(PurchaseProductDto dto) {
        return PurchaseProductCommand.builder()
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .build();
    }

    @DeleteMapping(value = "/api/v1/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        DeleteProductCommand command = newDeleteProductCommand(productId);
        commandGateway.send(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private DeleteProductCommand newDeleteProductCommand(String productId) {
        return DeleteProductCommand.builder()
                .productId(productId)
                .build();
    }
}
