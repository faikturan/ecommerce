package org.ashina.ecommerce.order.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.ashina.ecommerce.order.application.command.FulfillmentOrderCommand;
import org.ashina.ecommerce.order.application.error.ErrorCode;
import org.ashina.ecommerce.order.application.error.ServiceException;
import org.ashina.ecommerce.order.domain.FulfillmentTransaction;
import org.ashina.ecommerce.order.domain.Order;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.client.CartClient;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.model.GetCartDto;
import org.ashina.ecommerce.order.infrastructure.event.publisher.ReserveProductPublisher;
import org.ashina.ecommerce.order.infrastructure.persistence.repository.FulfillmentTransactionRepository;
import org.ashina.ecommerce.order.infrastructure.persistence.repository.OrderRepository;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.domain.DomainEntityIdentifierGenerator;
import org.ashina.ecommerce.sharedkernel.event.model.product.ReserveProductRequested;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FulfillmentOrderCommandHandler implements CommandHandler<FulfillmentOrderCommand, Void> {

    private final CartClient cartClient;
    private final OrderRepository orderRepository;
    private final FulfillmentTransactionRepository fulfillmentTransactionRepository;
    private final ReserveProductPublisher reserveProductPublisher;

    @Override
    public Class<?> support() {
        return FulfillmentOrderCommand.class;
    }

    @Override
    @Transactional
    public Void handle(FulfillmentOrderCommand command) {
        // Get cart and validate all products in stock
        GetCartDto cart = cartClient.getCart();
        validateAllProductsInStock(cart);

        // Create new order
        Order order = newOrder(command, cart);
        orderRepository.save(order);

        // Create new order transaction
        FulfillmentTransaction fulfillmentTransaction = newFulfillmentTransaction(order.getId());
        fulfillmentTransactionRepository.save(fulfillmentTransaction);

        // Publish product reserved request
        ReserveProductRequested reserveProductRequested = new ReserveProductRequested();
        reserveProductPublisher.publish(reserveProductRequested);

        return null;
    }

    public void validateAllProductsInStock(GetCartDto cart) {
        for (GetCartDto.Line line : cart.getLines()) {
            if (line.getQuantity() <= 0) {
                throw ServiceException.of(
                        ErrorCode.PRODUCT_OUT_OF_STOCK,
                        String.format("Product %s is out of stock", line.getProductId()),
                        HttpStatus.BAD_REQUEST
                );
            }
        }
    }

    private Order newOrder(FulfillmentOrderCommand command, GetCartDto cart) {
        Order order = new Order();
        order.setId(DomainEntityIdentifierGenerator.uuid());
        order.setCustomerId(command.getCustomerId());
        cart.getLines().forEach(cartLine -> {
            Order.Line orderLine = new Order.Line();
            orderLine.setProductId(cartLine.getProductId());
            orderLine.setProductName(cartLine.getProductName());
            orderLine.setProductImage(cartLine.getProductImage());
            orderLine.setProductPrice(cartLine.getProductPrice());
            orderLine.setQuantity(cartLine.getQuantity());
            order.addLine(orderLine);
        });
        order.setTotal(cart.getTotal());
        order.setFullName(command.getFullName());
        order.setPhoneNumber(command.getPhoneNumber());
        order.setAddress(command.getAddress());
        return order;
    }

    private FulfillmentTransaction newFulfillmentTransaction(String orderId) {
        FulfillmentTransaction transaction = new FulfillmentTransaction();
        transaction.setId(DomainEntityIdentifierGenerator.uuid());
        transaction.setOrderId(orderId);
        transaction.setStatus(FulfillmentTransaction.Status.ORDER_CREATED);
        return transaction;
    }

    private ReserveProductRequested newProductReservedRequest(String transactionId, List<Order.Line> orderLines) {
        ReserveProductRequested event = new ReserveProductRequested();
        event.setTransactionId(transactionId);
        orderLines.forEach(orderLine -> {
            ReserveProductRequested.Line eventLine = new ReserveProductRequested.Line();
            eventLine.setProductId(orderLine.getProductId());
            eventLine.setQuantity(orderLine.getQuantity());
            event.addLine(eventLine);
        });
        return event;
    }
}
