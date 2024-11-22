package com.alten.product.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author lanfouf
 **/
@Data
@Builder
public class RegisterResponseDTO {
    private String username;
    private String email;
}
