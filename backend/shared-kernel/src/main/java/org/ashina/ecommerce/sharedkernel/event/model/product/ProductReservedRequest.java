package org.ashina.ecommerce.sharedkernel.event.model.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.sharedkernel.event.model.DomainEvent;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductReservedRequest extends DomainEvent {

    @Data
    public static class Line {
        private String productId;
        private Integer quantity;
    }

    private String transactionId;
    private List<Line> lines = new ArrayList<>();

    public void addLine(Line line) {
        this.lines.add(line);
    }
}
