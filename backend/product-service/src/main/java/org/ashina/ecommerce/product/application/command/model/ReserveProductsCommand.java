package org.ashina.ecommerce.product.application.command.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Builder
public class ReserveProductsCommand {

    @Getter
    @Builder
    public static class Line {

        @NotBlank
        private final String productId;

        @NotNull
        @Positive
        private final Integer quantity;
    }

    @NotEmpty
    private final List<Line> lines;
}
