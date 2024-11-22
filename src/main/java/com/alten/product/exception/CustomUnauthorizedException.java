package com.alten.product.exception;

/**
 * @author lanfouf
 **/
public class CustomUnauthorizedException extends RuntimeException {
    public CustomUnauthorizedException(String message) {
        super(message);
    }
}
