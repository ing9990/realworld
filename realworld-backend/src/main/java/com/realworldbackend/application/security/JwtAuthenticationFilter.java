package com.realworldbackend.application.security;

import com.google.common.net.HttpHeaders;
import com.realworldbackend.infrastructure.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationToken != null) {
            String rawToken = authorizationToken.substring(6);
            String userId = jwtProvider.getSubject(rawToken).trim();
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        new UserPayload(Long.parseLong(userId)),
                        null,
                        Collections.emptyList());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        filterChain.doFilter(request, response);
    }
}
