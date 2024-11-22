package com.alten.product.exception;

/**
 * @author lanfouf
 **/
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
