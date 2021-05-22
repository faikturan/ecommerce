package org.ashina.ecommerce.sharedkernel.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DomainException extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;

    public static DomainException of(String errorCode, String errorMessage) {
        return new DomainException(errorCode, errorMessage);
    }
}
