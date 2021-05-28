package org.ashina.ecommerce.cart.application.query.model;

import lombok.Data;
import org.ashina.ecommerce.cart.domain.CartLine;
import org.ashina.ecommerce.cart.infrastructure.ecommerce.model.Product;
import org.ashina.ecommerce.sharedkernel.query.model.View;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetCartView extends View {

    @Data
    public static class Line {

        private String productId;
        private String productName;
        private Integer productPrice;
        private Integer quantity;

        public Line(CartLine cartLine, Product product) {
            this.productId = cartLine.getProductId();
            this.productName = product.getName();
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
