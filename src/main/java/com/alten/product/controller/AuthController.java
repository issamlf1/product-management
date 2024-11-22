package com.alten.product.controller;

import com.alten.product.dto.AuthResponseDTO;
import com.alten.product.dto.LoginRequestDTO;
import com.alten.product.dto.RegisterRequestDTO;
import com.alten.product.dto.RegisterResponseDTO;
import com.alten.product.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lanfouf
 **/
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/account")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
