package org.ashina.ecommerce.product.application.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.product.application.command.model.CreateProductCommand;
import org.ashina.ecommerce.product.application.command.model.DeleteProductCommand;
import org.ashina.ecommerce.product.application.query.model.GetProductsQuery;
import org.ashina.ecommerce.product.application.query.model.GetProductsView;
import org.ashina.ecommerce.product.application.rest.dto.CreateProductDto;
import org.ashina.ecommerce.product.application.rest.dto.GetProductsDto;
import org.ashina.ecommerce.product.application.rest.mapper.GetProductsMapper;
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

    @GetMapping("/api/v1/products")
    public ResponseEntity<GetProductsDto> getProducts(@RequestParam Collection<String> ids) {
        if (true) {
            throw new RuntimeException("aaaa");
        }
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

    @PostMapping("/api/v1/products")
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

    @DeleteMapping("/api/v1/products/{productId}")
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
