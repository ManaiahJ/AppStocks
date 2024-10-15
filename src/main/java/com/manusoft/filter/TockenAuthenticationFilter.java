package com.manusoft.filter;


import com.manusoft.util.AuthUtils;
import com.manusoft.util.CoockeUtils;
import com.manusoft.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Component
public class TockenAuthenticationFilter extends OncePerRequestFilter {

    private static List<String> SECURED_URL_LIST = Arrays.asList("/views/users/", "/views/user", "/views/delete");
    private static final String PROJECT_CONTEXT_NAME = "/stocks";
    private static List<String> IGNORED_URL_LIST = Arrays.asList("/assets/");
    /**
     * This method filters incoming requests, checks for JWT token validity, and sets up authentication.
     * It removes the project context name from the request URI, checks if the URL is secured,
     * validates the JWT token, and sets up authentication if necessary.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if an exception occurs that interferes with the filter's normal operation
     * @throws IOException if an I/O error occurs during the operation
     */

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI().replaceFirst(PROJECT_CONTEXT_NAME, "");
        // Check if the URL is secured
        boolean isSecuredUrl = SECURED_URL_LIST.stream().anyMatch(path::startsWith);
        // Check if the URL is ignored by the filter
        if(isSecuredUrl){
           final String jwtToken = CoockeUtils.getCookieValue(JWTUtil.USER_ID_COOKIE_NAME, request);

            if(jwtToken == null || jwtToken.isEmpty() || jwtToken.startsWith("Bearer ")){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
            boolean value = JWTUtil.isTokenValid(jwtToken);

            if(!value){
                response.sendError(440, "Token Expired or Invalid");
                return;
            }
            AuthUtils.setAuthentication(jwtToken);
        }
        filterChain.doFilter(request, response);
    }
}
