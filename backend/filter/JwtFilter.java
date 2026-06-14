package com.expanse.expanse.manager.filter;

import com.expanse.expanse.manager.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null){
            try {
                boolean isValidated = jwtService.validateToken(token);
                if (isValidated){
                    String username  = jwtService.extractUsername(token);
                    Authentication authentication =  new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority("admin")));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
//                filterChain.doFilter(request,response);

            } catch (Exception e){
                log.error(e.getMessage());
            }
        }

        filterChain.doFilter(request,response);
    }
}
