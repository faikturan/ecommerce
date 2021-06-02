package org.ashina.ecommerce.product.application.command.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Map;

@Getter
@Builder
public class CreateProductCommand {

    @NotBlank
    @Size(max = 255)
    private final String name;

    @NotBlank
    @Size(max = 1000)
    private final String description;

    @NotBlank
    @Size(max = 255)
    private final String image;

    @NotNull
    @Positive
    private final Integer price;

    private final Map<String, Object> attributes;
}
