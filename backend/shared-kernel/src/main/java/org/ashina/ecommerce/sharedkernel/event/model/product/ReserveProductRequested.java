package org.ashina.ecommerce.sharedkernel.event.model.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.ashina.ecommerce.sharedkernel.event.model.FulfillmentOrderRequested;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReserveProductRequested extends FulfillmentOrderRequested {

    @Data
    public static class Line {
        private String productId;
        private Integer quantity;
    }

    private List<Line> lines = new ArrayList<>();

    public void addLine(Line line) {
        this.lines.add(line);
    }
}
