package org.ashina.ecommerce.product.application.rest.handler;

import lombok.Data;
import org.ashina.ecommerce.product.application.error.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {

    @Data
    public static class Response {
        private String errorCode;
        private String errorMessage;
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Response> handleServiceException(ServiceException e) {
        Response response = new Response();
        response.setErrorCode(e.getErrorCode());
        response.setErrorMessage(e.getErrorMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }
}
