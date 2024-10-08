package com.anaplan.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Added security(basic authentication) to restrict the /manage endpoints and some of the dashboards API's
 */
@Configuration
public class SecurityConfig {
    @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http .authorizeHttpRequests((authz) ->
                authz.requestMatchers("/**")
                        .permitAll()
                        .anyRequest().authenticated())
                        .csrf(csrf -> csrf.disable());
        return http.build();
    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
