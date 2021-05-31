package org.ashina.ecommerce.cart.application.error;

public class ErrorCode {

    private ErrorCode() {
    }

    public static final String CART_NOT_FOUND = "cart.not_found";
    public static final String LINE_QUANTITY_INVALID = "line.quantity.invalid";
    public static final String PRODUCT_NOT_FOUND = "product.not_found";
}