package org.ashina.ecommerce.product.application.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class RefundProductsDto {

    @Data
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
