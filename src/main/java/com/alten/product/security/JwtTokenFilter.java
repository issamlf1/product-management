package com.alten.product.security;

import com.alten.product.data.User;
import com.alten.product.exception.CustomUnauthorizedException;
import com.alten.product.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * @author lanfouf
 **/
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {
        String token = resolveToken(request);
        if (token != null) {
            if (!jwtTokenProvider.validateToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Token is not valid.\"}");
                response.setContentType("application/json");
                return;
            }

            String email = jwtTokenProvider.getUsername(token);
            User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomUnauthorizedException("User not found"));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    user.getEmail(), null, List.of(new SimpleGrantedAuthority(user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER")));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7).trim();
        }
        return null;
    }
}
