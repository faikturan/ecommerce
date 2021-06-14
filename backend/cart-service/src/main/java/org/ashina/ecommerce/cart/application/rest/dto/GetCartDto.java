package org.ashina.ecommerce.cart.application.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetCartDto {

    @Getter
    @Setter
    public static class Line {
        private String productId;
        private String productName;
        private String productImage;
        private Integer productPrice;
        private Integer quantity;
    }

    private List<Line> lines;
    private Integer total;
}
