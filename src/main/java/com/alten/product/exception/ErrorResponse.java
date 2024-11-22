package com.alten.product.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author lanfouf
 **/
 @Getter
 @Setter
 @AllArgsConstructor
 @NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
}
