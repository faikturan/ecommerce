package org.ashina.ecommerce.cart.application.query.model;

import lombok.Data;
import org.ashina.ecommerce.cart.domain.Cart;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.feign.model.GetProductsDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetCartView {

    @Data
    public static class Line {

        private String productId;
        private String productName;
        private String productImage;
        private Integer productPrice;
        private Integer quantity;

        public Line(Cart.Line cartLine, GetProductsDto.Product product) {
            this.productId = cartLine.getProductId();
            this.productName = product.getName();
            this.productImage = product.getImage();
            this.productPrice = product.getPrice();
            this.quantity = cartLine.getQuantity();
        }
    }

    private List<Line> lines = new ArrayList<>();
    private Integer total;

    public void addLine(Line line) {
        this.lines.add(line);
    }
}
