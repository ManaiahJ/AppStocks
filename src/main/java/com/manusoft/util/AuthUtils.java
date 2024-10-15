package com.manusoft.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Objects;

public class AuthUtils {
    /**
     * Sets the authentication token in the security context.
     *
     * @param token the authentication token to set
     */

    public static void setAuthentication(String token) {
        SecurityContext sc = SecurityContextHolder.getContext();
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(token, null, new ArrayList<>());
        sc.setAuthentication(authReq);
    }
    /**
     * Removes authentication credentials from the security context.
     */
    public static void removeAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if(Objects.nonNull(authentication) && authentication instanceof  UsernamePasswordAuthenticationToken ){
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            token.eraseCredentials();
        }
        SecurityContextHolder.clearContext();
    }
}
