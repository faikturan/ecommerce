package org.ashina.ecommerce.catalog.application.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Map;

@Data
public class CreateProductDto {

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotBlank
    @Size(max = 255)
    private String image;

    @NotNull
    @Positive
    private Integer price;

    private Map<String, Object> attributes;
}
