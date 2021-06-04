package org.ashina.ecommerce.product.application.rest.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
public class ReserveProductsDto {

    @Getter
    @Setter
    public static class Line {

        @NotBlank
        private String productId;

        @NotNull
        @Positive
        private Integer quantity;
    }

    @NotEmpty
    private List<Line> lines;
}
