package com.alten.product.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author lanfouf
 **/
@Data
public class RegisterRequestDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String firstname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
