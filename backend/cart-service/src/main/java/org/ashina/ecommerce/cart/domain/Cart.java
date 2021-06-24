package org.ashina.ecommerce.cart.domain;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.cart.infrastructure.persistence.entity.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "carts")
@Getter
@Setter
public class Cart extends BaseEntity {

    @Getter
    @Setter
    public static class Line {

        private String productId;
        private Integer quantity;

        public static final int MAX_QUANTITY = 10;
    }

    private String customerId;
    private List<Line> lines = new ArrayList<>();

    public void addLine(Line line) {
        this.lines.add(line);
    }
}
