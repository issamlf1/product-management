package com.alten.product.exception;

/**
 * @author lanfouf
 **/
public class CustomConflictException extends RuntimeException {
    public CustomConflictException(String message) {
        super(message);
    }
}
