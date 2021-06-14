package org.ashina.ecommerce.order.infrastructure.ecommerce.feign.model;

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

    private List<Line> lines = new ArrayList<>();
    private Integer total = 0;
}
