package com.alten.product.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author lanfouf
 **/
public class SecurityUtils {
    private SecurityUtils() {}
    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && (authentication.getPrincipal() instanceof String s))
            return s;
        return null;
    }
}
