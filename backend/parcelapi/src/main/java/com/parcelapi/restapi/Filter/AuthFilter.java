package com.parcelapi.restapi.Filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.parcelapi.restapi.Services.AuthService;
import com.parcelapi.restapi.Utility.JWTUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthFilter extends OncePerRequestFilter{

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = null, userName = null;
        
        if(authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            userName = jwtUtility.getUsernameFromToken(token);
        }

        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
            UserDetails userDetails = authService.loadUserByUsername(userName);
            
            if(jwtUtility.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken UPToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                UPToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(UPToken);
            }
        }

        filterChain.doFilter(request, response);
    }
    
}
