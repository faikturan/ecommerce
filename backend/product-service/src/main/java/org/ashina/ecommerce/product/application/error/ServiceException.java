package org.ashina.ecommerce.product.application.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class ServiceException extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    public static ServiceException of(String errorCode, String errorMessage, HttpStatus httpStatus) {
        return new ServiceException(errorCode, errorMessage, httpStatus);
    }
}
