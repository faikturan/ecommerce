package org.ashina.ecommerce.order.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.order.application.command.CancelOrderCommand;
import org.ashina.ecommerce.order.application.error.ErrorCode;
import org.ashina.ecommerce.order.application.error.ServiceException;
import org.ashina.ecommerce.order.domain.Order;
import org.ashina.ecommerce.order.infrastructure.event.publisher.OrderCanceledPublisher;
import org.ashina.ecommerce.order.infrastructure.persistence.repository.OrderRepository;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCanceled;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CancelOrderCommandHandler implements CommandHandler<CancelOrderCommand, Void> {

    private final OrderRepository orderRepository;
    private final OrderCanceledPublisher orderCanceledPublisher;

    @Override
    public Class<?> support() {
        return CancelOrderCommand.class;
    }

    @Override
    @Transactional
    public Void handle(CancelOrderCommand command) {
        // Get order
        Order order = orderRepository.findById(command.getOrderId())
                .orElseThrow(() -> ServiceException.of(
                        ErrorCode.ORDER_NOT_FOUND,
                        String.format("Order %s not found", command.getOrderId()),
                        HttpStatus.NOT_FOUND
                ));

        // Validate permission
        if (!command.getCustomerId().equals(order.getCustomerId())) {
            throw ServiceException.of(
                    ErrorCode.PERMISSION,
                    "You do not have permission to cancel order",
                    HttpStatus.FORBIDDEN
            );
        }

        // Change order status
        order.setStatus(OrderStatus.CANCELED);

        // Save order
        orderRepository.save(order);

        // Publish event
        OrderCanceled event = newEvent(order);
        orderCanceledPublisher.publish(event);

        return null;
    }

    private OrderCanceled newEvent(Order order) {
        OrderCanceled event = new OrderCanceled();
        event.setOrderId(order.getId());
        order.getLines().forEach(orderLine -> {
            OrderCanceled.Line eventLine = new OrderCanceled.Line();
            eventLine.setProductId(orderLine.getProductId());
            eventLine.setQuantity(orderLine.getQuantity());
            event.addLine(eventLine);
        });
        return event;
    }
}
