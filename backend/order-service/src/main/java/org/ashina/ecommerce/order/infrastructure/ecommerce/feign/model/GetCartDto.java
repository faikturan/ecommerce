package org.ashina.ecommerce.order.infrastructure.ecommerce.feign.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetCartDto {

    @Data
    public static class Line {
        private String productId;
        private String productName;
        private String productImage;
        private Integer productPrice;
        private Integer quantity;
    }

    private List<Line> lines = new ArrayList<>();
    private Integer total;
}
