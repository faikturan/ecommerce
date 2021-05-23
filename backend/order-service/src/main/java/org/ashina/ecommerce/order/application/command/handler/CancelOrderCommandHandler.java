package org.ashina.ecommerce.order.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.order.application.command.CancelOrderCommand;
import org.ashina.ecommerce.order.application.error.ErrorCode;
import org.ashina.ecommerce.order.application.error.ServiceException;
import org.ashina.ecommerce.order.domain.Order;
import org.ashina.ecommerce.order.domain.OrderStatus;
import org.ashina.ecommerce.order.infrastructure.event.publisher.OrderCanceledPublisher;
import org.ashina.ecommerce.order.infrastructure.persistence.OrderPersistence;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.command.model.Command;
import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCanceled;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class CancelOrderCommandHandler implements CommandHandler<CancelOrderCommand> {

    private final OrderPersistence orderPersistence;
    private final OrderCanceledPublisher orderCanceledPublisher;

    @Override
    public Class<? extends Command> support() {
        return CancelOrderCommand.class;
    }

    @Override
    @Transactional
    public void handle(CancelOrderCommand command) {
        // Get order
        Order order = orderPersistence.findById(command.getOrderId())
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
        orderPersistence.save(order);

        // Publish event
        OrderCanceled event = newEvent(order);
        orderCanceledPublisher.publish(event);
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
