package org.ashina.ecommerce.order.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.order.application.command.CreateOrderCommand;
import org.ashina.ecommerce.order.application.error.ErrorCode;
import org.ashina.ecommerce.order.application.error.ServiceException;
import org.ashina.ecommerce.order.domain.CartLine;
import org.ashina.ecommerce.order.domain.Order;
import org.ashina.ecommerce.order.domain.OrderLine;
import org.ashina.ecommerce.order.domain.OrderStatus;
import org.ashina.ecommerce.order.infrastructure.ecommerce.CatalogService;
import org.ashina.ecommerce.order.infrastructure.ecommerce.InventoryService;
import org.ashina.ecommerce.order.infrastructure.ecommerce.model.Product;
import org.ashina.ecommerce.order.infrastructure.event.publisher.OrderCreatedPublisher;
import org.ashina.ecommerce.order.infrastructure.persistence.CartLinePersistence;
import org.ashina.ecommerce.order.infrastructure.persistence.OrderPersistence;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.command.model.Command;
import org.ashina.ecommerce.sharedkernel.domain.DomainEntityIdentifierGenerator;
import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCreated;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CreateOrderCommandHandler implements CommandHandler<CreateOrderCommand> {

    private final OrderPersistence orderPersistence;
    private final CartLinePersistence cartLinePersistence;
    private final CatalogService catalogService;
    private final InventoryService inventoryService;
    private final OrderCreatedPublisher orderCreatedPublisher;

    @Override
    public Class<? extends Command> support() {
        return CreateOrderCommand.class;
    }

    @Override
    @Transactional
    public void handle(CreateOrderCommand command) {
        // Get cart lines
        List<CartLine> cartLines = cartLinePersistence.findByCustomerId(command.getCustomerId());
        if (CollectionUtils.isEmpty(cartLines)) {
            throw ServiceException.of(
                ErrorCode.CART_EMPTY,
                "You does not have any products in cart",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        List<String> productIds = cartLines
                .stream()
                .map(CartLine::getProductId)
                .collect(Collectors.toList());

        // Check out of stock
        Map<String, Integer> stockMap = inventoryService.getStocks(productIds);
        checkOutOfStock(stockMap);

        // Get products
        Map<String, Product> productMap = catalogService.getProducts(productIds);

        // Create order
        Order order = newOrder(command.getCustomerId(), cartLines, productMap);

        // Save order
        orderPersistence.save(order);

        // Publish event
        OrderCreated event = newEvent(order);
        orderCreatedPublisher.publish(event);
    }

    private void checkOutOfStock(Map<String, Integer> stockMap) {
        for (Map.Entry<String, Integer> entry : stockMap.entrySet()) {
            if (entry.getValue() == 0) {
                throw ServiceException.of(
                        ErrorCode.PRODUCT_OUT_OF_STOCK,
                        String.format("Product %s is out of stock now", entry.getKey()),
                        HttpStatus.INTERNAL_SERVER_ERROR
                );
            }
        }
    }

    private Order newOrder(String customerId, List<CartLine> cartLines, Map<String, Product> productMap) {
        Order order = new Order(DomainEntityIdentifierGenerator.uuid());
        order.setCustomerId(customerId);
        order.setStatus(OrderStatus.CREATED);
        List<OrderLine> orderLines = cartLines
                .stream()
                .map(cartLine -> {
                    OrderLine orderLine = new OrderLine();
                    orderLine.setProductId(cartLine.getProductId());
                    orderLine.setProductPrice(productMap.get(cartLine.getProductId()).getPrice());
                    orderLine.setQuantity(cartLine.getQuantity());
                    return orderLine;
                })
                .collect(Collectors.toList());
        order.setLines(orderLines);
        return order;
    }

    private OrderCreated newEvent(Order order) {
        OrderCreated event = new OrderCreated();
        event.setOrderId(order.getId());
        order.getLines().forEach(orderLine -> {
            OrderCreated.Line eventLine = new OrderCreated.Line();
            eventLine.setProductId(orderLine.getProductId());
            eventLine.setQuantity(orderLine.getQuantity());
            event.addLine(eventLine);
        });
        return event;
    }
}
