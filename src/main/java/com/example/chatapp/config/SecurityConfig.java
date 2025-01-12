package com.example.chatapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll() // Allow access to login and register pages
                        .anyRequest().authenticated() // Secure all other endpoints
                )
                .formLogin(form -> form
                        .loginPage("/login") // Static login page
                        .defaultSuccessUrl("/chat.html", true) // Redirect after successful login
                        .failureUrl("/login?error=true") // Redirect here on login failure
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("uniqueAndSecret") // A secret key used to sign remember-me cookies
                        .tokenValiditySeconds(7 * 24 * 60 * 60) // Cookie validity: 7 days
                        .rememberMeParameter("remember-me") // Name of the checkbox in your form
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID", "remember-me") // Clear session and remember-me cookies
                );


        return http.build();
    }

    


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

