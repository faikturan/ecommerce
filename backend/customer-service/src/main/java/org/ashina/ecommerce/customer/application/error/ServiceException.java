package org.ashina.ecommerce.customer.application.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ServiceException extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    public static ServiceException of(String errorCode, String errorMessage, HttpStatus httpStatus) {
        return new ServiceException(errorCode, errorMessage, httpStatus);
    }
}
