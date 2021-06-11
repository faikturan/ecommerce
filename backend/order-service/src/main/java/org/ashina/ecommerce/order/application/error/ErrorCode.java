package org.ashina.ecommerce.order.application.error;

public class ErrorCode {

    private ErrorCode() {
    }

    public static final String CART_EMPTY = "cart.empty";
    public static final String PRODUCT_OUT_OF_STOCK = "product.out_of_stock";
    public static final String ORDER_FULFILLMENT_RESERVE_PRODUCTS_FAILED = "order.fulfillment.reserve_products_failed";
    public static final String ORDER_FULFILLMENT_PROCESS_PAYMENT_FAILED = "order.fulfillment.process_payment_failed";
}