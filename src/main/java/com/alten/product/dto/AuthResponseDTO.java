package com.alten.product.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author lanfouf
 **/
@Data
@Builder
public class AuthResponseDTO {
    private String token;
    private String username;
    private String email;
    private boolean isAdmin;



}
