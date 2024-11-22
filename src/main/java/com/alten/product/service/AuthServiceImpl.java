package com.alten.product.service;

import com.alten.product.data.User;
import com.alten.product.dto.AuthResponseDTO;
import com.alten.product.dto.LoginRequestDTO;
import com.alten.product.dto.RegisterRequestDTO;
import com.alten.product.dto.RegisterResponseDTO;
import com.alten.product.exception.CustomConflictException;
import com.alten.product.exception.CustomUnauthorizedException;
import com.alten.product.repository.UserRepository;
import com.alten.product.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.alten.product.security.SecurityUtils.getCurrentUserEmail;

/**
 * @author lanfouf
 **/
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new CustomConflictException("Email already exists");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .firstname(registerRequest.getFirstname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .isAdmin(registerRequest.getEmail().equals("admin@admin.com"))
                .build();

        userRepository.save(user);

        return RegisterResponseDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomUnauthorizedException("Invalid email or password"));

        String role = user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";

        String token = jwtTokenProvider.createToken(user.getEmail(), List.of(role));

        return AuthResponseDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .isAdmin(user.isAdmin())
                .token(token)
                .build();
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomUnauthorizedException("User not authenticated"));
    }


}
