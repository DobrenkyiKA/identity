package com.kdob.piq.user.infrastructure.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class GatewayAuthFilter extends OncePerRequestFilter {

    public static final String USER_ID_HEADER = "X-User-Id";
    public static final String USER_EMAIL_HEADER = "X-User-Email";
    public static final String USER_ROLES_HEADER = "X-User-Roles";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String userId = request.getHeader(USER_ID_HEADER);

        if (userId != null) {
            String email = request.getHeader(USER_EMAIL_HEADER);
            if (email == null) email = "";

            String rolesHeader = request.getHeader(USER_ROLES_HEADER);
            List<SimpleGrantedAuthority> authorities;
            if (rolesHeader != null && !rolesHeader.isBlank()) {
                authorities = Arrays.stream(rolesHeader.split(","))
                        .filter(r -> !r.isBlank())
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                        .toList();
            } else {
                authorities = Collections.emptyList();
            }

            GatewayPrincipal principal = new GatewayPrincipal(userId, email);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(principal, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}