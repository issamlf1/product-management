package com.alten.product.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author lanfouf
 **/
@Data
public class LoginRequestDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
