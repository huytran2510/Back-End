package com.example.backendproject.config;

import com.example.backendproject.filter.JwtFilter;
import com.example.backendproject.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

    @Autowired
    private JwtFilter jwtFilter;


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(customPasswordEncoder.getPasswordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http = http.csrf().disable().cors().disable();

        http = http.sessionManagement()
                .sessionCreationPolicy((SessionCreationPolicy.STATELESS))
                .and();

        http = http.exceptionHandling()
                .authenticationEntryPoint((request,response,ex) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                }).and();

        http.authorizeHttpRequests()
                .antMatchers("/api/auth/**", "/ws/**", "/chatroom/**",
                        "/private-message/**","/messsage","/chatroom/public",
                        "/api/products/**", "/api/reviews/**","/api/payment/**","/api/place-order/**",
                        "/api/discount/**","/api/category/**","/api/reports/**",
                        "/api/users/**").permitAll()

                .anyRequest().authenticated();

        http.cors().configurationSource(request -> {
            CorsConfiguration cors = new CorsConfiguration();
            cors.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // or "*"
            cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
            cors.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
            return cors;
        });

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
