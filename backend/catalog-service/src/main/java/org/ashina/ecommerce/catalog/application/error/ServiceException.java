package org.ashina.ecommerce.catalog.application.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ServiceException extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;

    public static ServiceException of(String errorCode, String errorMessage) {
        return new ServiceException(errorCode, errorMessage);
    }
}
