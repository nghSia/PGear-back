package com.jpo.pgearback.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService v_userDetailsService;

    private final JwtUtil v_jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String v_authHeader = request.getHeader("Authorization");
        String v_token = null;
        String v_username = null;

        if (v_authHeader != null && v_authHeader.startsWith("Bearer ")) {
            v_token = v_authHeader.substring(7);
            v_username = v_jwtUtil.extractUsername(v_token);
        }

        if(v_username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails v_userDetails = v_userDetailsService.loadUserByUsername(v_username);

            if(v_jwtUtil.validateToken(v_token, v_userDetails)){
                UsernamePasswordAuthenticationToken v_authToken = new UsernamePasswordAuthenticationToken(v_userDetails, null, v_userDetails.getAuthorities());
                v_authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(v_authToken);
            }
        }

        filterChain.doFilter(request, response);
    }


}
