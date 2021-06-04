package org.ashina.ecommerce.payment.application.rest.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ValidationExceptionHandler {

    @Getter
    @Setter
    public static class Response {

        @Getter
        @Setter
        @AllArgsConstructor
        public static class Error {
            private String field;
            private String message;
        }

        private List<Error> errors = new ArrayList<>();

        public void addError(Error error) {
            this.errors.add(error);
        }
    }

    /**
     * Catch BindingException from GET body
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Response> handleBindException(BindException e) {
        Response response = new Response();

        List<FieldError> fieldErrors = e.getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            response.addError(new Response.Error(field, message));
        });

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Catch MethodArgumentNotValidException thrown when validate @RequestBody
     * Note: please add @Valid before @RequestBody
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        Response response = new Response();

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            response.addError(new Response.Error(field, message));
        });

        return ResponseEntity.badRequest().body(response);
    }
}
