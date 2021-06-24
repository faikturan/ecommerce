package org.ashina.ecommerce.order.application.command.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashina.ecommerce.order.application.command.FulfillmentOrderCommand;
import org.ashina.ecommerce.order.application.error.ErrorCode;
import org.ashina.ecommerce.order.application.error.ServiceException;
import org.ashina.ecommerce.order.domain.Order;
import org.ashina.ecommerce.order.domain.OrderStatus;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.client.CartClient;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.client.PaymentClient;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.client.ProductClient;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.model.GetCartDto;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.model.ProcessPaymentDto;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.model.RefundProductsDto;
import org.ashina.ecommerce.order.infrastructure.ecommerce.feign.model.ReserveProductsDto;
import org.ashina.ecommerce.order.infrastructure.event.publisher.OrderCompletedPublisher;
import org.ashina.ecommerce.order.infrastructure.persistence.repository.OrderRepository;
import org.ashina.ecommerce.sharedkernel.command.handler.CommandHandler;
import org.ashina.ecommerce.sharedkernel.event.model.order.OrderCompleted;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FulfillmentOrderCommandHandler implements CommandHandler<FulfillmentOrderCommand, Void> {

    private final OrderRepository orderRepository;
    private final CartClient cartClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final OrderCompletedPublisher orderCompletedPublisher;

    @Override
    public Class<?> support() {
        return FulfillmentOrderCommand.class;
    }

    @Override
    public Void handle(FulfillmentOrderCommand command) {
        // Get cart and validate all products in stock
        GetCartDto cart = cartClient.getCart();
        validateCart(cart);

        // Create new order
        Order order = createOrder(command, cart);
        log.info("Created order #{}", order.getId());

        // Reserve products
        try {
            reserveProducts(order.getLines());
            log.info("[Fulfillment order #{}] Reserve products successful", order.getId());
        } catch (Exception e) {
            log.error("[Fulfillment order #{}] Reserve products failed", order.getId());
            throw ServiceException.of(
                    ErrorCode.ORDER_FULFILLMENT_RESERVE_PRODUCTS_FAILED,
                    String.format("[Fulfillment order #%s] Reserve products failed", order.getId()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        // Process payment
        try {
            processPayment(order);
            log.info("[Fulfillment order #{}] Process payment successful", order.getId());
        } catch (Exception e) {
            log.error("[Fulfillment order #{}] Process payment failed", order.getId());
            rollbackReserveProducts(order.getLines());
            throw ServiceException.of(
                    ErrorCode.ORDER_FULFILLMENT_PROCESS_PAYMENT_FAILED,
                    String.format("[Fulfillment order #%s] Process payment failed", order.getId()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        // Complete order
        completeOrder(order);

        return null;
    }

    private void validateCart(GetCartDto cart) {
        if (CollectionUtils.isEmpty(cart.getLines())) {
            throw ServiceException.of(
                    ErrorCode.CART_EMPTY,
                    "Cart is empty",
                    HttpStatus.BAD_REQUEST
            );
        }

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

    // Create order
    // -----------------------------------------------------------------------------------------------------------------

    private Order createOrder(FulfillmentOrderCommand command, GetCartDto cart) {
        Order order = newOrder(command, cart);
        return orderRepository.save(order);
    }

    private Order newOrder(FulfillmentOrderCommand command, GetCartDto cart) {
        Order order = new Order();
        order.setCustomerId(command.getCustomerId());
        cart.getLines().forEach(cartLine -> {
            Order.Line orderLine = newOrderLine(cartLine);
            order.addLine(orderLine);
        });
        order.setTotal(cart.getTotal());
        Order.Recipient recipient = newOrderRecipient(command);
        order.setRecipient(recipient);
        order.setStatus(OrderStatus.CREATED);
        return order;
    }

    private Order.Line newOrderLine(GetCartDto.Line cartLine) {
        Order.Line orderLine = new Order.Line();
        orderLine.setProductId(cartLine.getProductId());
        orderLine.setProductName(cartLine.getProductName());
        orderLine.setProductImage(cartLine.getProductImage());
        orderLine.setProductPrice(cartLine.getProductPrice());
        orderLine.setQuantity(cartLine.getQuantity());
        return orderLine;
    }

    private Order.Recipient newOrderRecipient(FulfillmentOrderCommand command) {
        Order.Recipient recipient = new Order.Recipient();
        recipient.setName(command.getName());
        recipient.setPhoneNumber(command.getPhoneNumber());
        recipient.setAddress(command.getAddress());
        return recipient;
    }

    // Reserve products
    // -----------------------------------------------------------------------------------------------------------------

    private void reserveProducts(List<Order.Line> cartLines) {
        ReserveProductsDto dto = asReserveProductsDto(cartLines);
        productClient.reserveProducts(dto);
    }

    private ReserveProductsDto asReserveProductsDto(List<Order.Line> orderLines) {
        ReserveProductsDto dto = new ReserveProductsDto();
        orderLines.forEach(orderLine -> {
            ReserveProductsDto.Line dtoLine = new ReserveProductsDto.Line();
            dtoLine.setProductId(orderLine.getProductId());
            dtoLine.setQuantity(orderLine.getQuantity());
            dto.addLine(dtoLine);
        });
        return dto;
    }

    private void rollbackReserveProducts(List<Order.Line> orderLines) {
        RefundProductsDto dto = asRefundProductsDto(orderLines);
        productClient.refundProducts(dto);
    }

    private RefundProductsDto asRefundProductsDto(List<Order.Line> orderLines) {
        RefundProductsDto dto = new RefundProductsDto();
        orderLines.forEach(orderLine -> {
            RefundProductsDto.Line dtoLine = new RefundProductsDto.Line();
            dtoLine.setProductId(orderLine.getProductId());
            dtoLine.setQuantity(orderLine.getQuantity());
            dto.addLine(dtoLine);
        });
        return dto;
    }

    // Process payment
    // -----------------------------------------------------------------------------------------------------------------

    private void processPayment(Order order) {
        ProcessPaymentDto dto = asProcessPaymentDto(order);
        paymentClient.processPayment(dto);

        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }

    private ProcessPaymentDto asProcessPaymentDto(Order order) {
        ProcessPaymentDto dto = new ProcessPaymentDto();
        dto.setCustomerId(order.getCustomerId());
        dto.setOrderId(order.getId());
        dto.setAmount(order.getTotal());
        return dto;
    }

    // Complete order
    // -----------------------------------------------------------------------------------------------------------------

    private void completeOrder(Order order) {
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);

        OrderCompleted event = newOrderCompleted(order);
        orderCompletedPublisher.publish(event);
    }

    private OrderCompleted newOrderCompleted(Order order) {
        OrderCompleted event = new OrderCompleted();
        event.setCustomerId(order.getCustomerId());
        event.setOrderId(order.getId());
        return event;
    }
}
