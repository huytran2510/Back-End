package com.example.backendproject.filter;

import com.example.backendproject.repository.UserRepository;
import com.example.backendproject.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get the Authorization header from the request
        final String authorizationHeader = request.getHeader("Authorization");

        // Check if the Authorization header is missing or doesn't start with "Bearer "
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            // If the header is not present or does not start with "Bearer ", continue the filter chain without processing
            filterChain.doFilter(request, response);
            return;
        }

        String username = null;
        String jwtToken = null;

        // Extract the token from the Authorization header (removing the "Bearer " prefix)
        jwtToken = authorizationHeader.substring(7);

        try {
            // Extract username from token
            username = jwtUtil.getUsernameFromToken(jwtToken);
        } catch (Exception e) {
            System.out.println("Invalid JWT Token: " + e.getMessage());
        }

        // If the username is not null and the SecurityContext is empty
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load user details from the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Validate the token with user details
            if (jwtUtil.validateToken(jwtToken, userDetails)) {

                // Create the authentication token and set the authentication context
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
