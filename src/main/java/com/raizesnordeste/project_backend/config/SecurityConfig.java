package com.raizesnordeste.project_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auditoria/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/pedidos").hasAnyRole("CLIENTE", "ATENDENTE", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/pedidos").hasAnyRole("CLIENTE", "ATENDENTE", "GERENTE", "ADMIN")
                .requestMatchers("/pedidos/*/pagamentos").hasAnyRole("ATENDENTE", "GERENTE", "ADMIN")
                .requestMatchers(
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/swagger-ui",
                    "/swagger-ui/**",
                    "/swagger-ui.html"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("123456"))
            .roles("ADMIN")
            .build();

        UserDetails gerente = User.builder()
            .username("gerente")
            .password(passwordEncoder.encode("123456"))
            .roles("GERENTE")
            .build();

        UserDetails atendente = User.builder()
            .username("atendente")
            .password(passwordEncoder.encode("123456"))
            .roles("ATENDENTE")
            .build();

        UserDetails cliente = User.builder()
            .username("cliente")
            .password(passwordEncoder.encode("123456"))
            .roles("CLIENTE")
            .build();

        return new InMemoryUserDetailsManager(admin, gerente, atendente, cliente);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
