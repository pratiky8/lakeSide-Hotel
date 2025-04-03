package com.athina.lakesidehotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // 🔹 Disable CSRF for easier testing (Optional)
                .authorizeHttpRequests(auth -> auth
                                /* .requestMatchers("/rooms/add/new-room", "/rooms/test-auth")*/.anyRequest().permitAll()  // 🔓 Allow these endpoints without authentication
                        /*.anyRequest().authenticated() */ // 🔒 Protect all other endpoints
                        // to open all .anyRequest() before .permitAll() and delete  .requestMatchers("/rooms/add/new-room", "/rooms/test-auth") and .anyRequest().authenticated()
                )
                .httpBasic();  // 🛡️ Keep Basic Authentication enabled

        return http.build();
    }
}
