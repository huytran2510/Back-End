package com.example.backendproject.controller;

import com.example.backendproject.domain.dto.AuthCredentialRequest;
import com.example.backendproject.domain.model.Customer;
import com.example.backendproject.domain.model.User;
import com.example.backendproject.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthCredentialRequest req) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    req.getUsername(), req.getPassword()
                            )
                    );
            UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);
            System.out.println("token: " + token);
            System.out.println(userDetails.getAuthorities().toString());
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            token
                    )
                    .body(userDetails);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token, @AuthenticationPrincipal User user) {
        Boolean isValidToken = jwtUtil.validateToken(token,user);
        return ResponseEntity.ok(isValidToken);
    }
}
