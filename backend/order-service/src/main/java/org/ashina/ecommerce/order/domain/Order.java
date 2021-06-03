package org.ashina.ecommerce.order.domain;

import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.order.infrastructure.persistence.entity.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders")
@Getter
@Setter
public class Order extends BaseEntity {

    @Getter
    @Setter
    public static class Line {

        private String productId;
        private String productName;
        private String productImage;
        private Integer productPrice;
        private Integer quantity;

        public static final int MAX_QUANTITY = 10;
    }

    private String customerId;
    private List<Line> lines = new ArrayList<>();
    private Integer total;
    private String fullName;
    private String phoneNumber;
    private String address;

    public void addLine(Line line) {
        this.lines.add(line);
    }
}
