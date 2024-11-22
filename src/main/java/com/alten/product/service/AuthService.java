package com.alten.product.service;

import com.alten.product.data.User;
import com.alten.product.dto.AuthResponseDTO;
import com.alten.product.dto.LoginRequestDTO;
import com.alten.product.dto.RegisterRequestDTO;
import com.alten.product.dto.RegisterResponseDTO;

/**
 * @author lanfouf
 **/
public interface AuthService {
    RegisterResponseDTO register(RegisterRequestDTO registerRequest);

    AuthResponseDTO login(LoginRequestDTO loginRequest);

    User getCurrentUser();
}
