package com.example.ticket_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class security {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // USER
                        // =========================

                        .requestMatchers(HttpMethod.POST, "/api/users")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/users/**")
                        .authenticated()

                        .requestMatchers(HttpMethod.PUT, "/api/users/**")
                        .hasRole("AGENT")

                        .requestMatchers(HttpMethod.DELETE, "/api/users/**")
                        .hasRole("AGENT")


                        // =========================
                        // TICKET
                        // =========================

                        // Agent bütün ticketları görebilir
                        .requestMatchers(HttpMethod.GET, "/api/tickets")
                        .hasRole("AGENT")

                        // Customer sadece kendi ticketlarını görebilir
                        .requestMatchers(HttpMethod.GET, "/api/tickets/my")
                        .hasRole("CUSTOMER")

                        // Tek ticket görüntüleme
                        // Ownership kontrolünü Service yapıyor
                        .requestMatchers(HttpMethod.GET, "/api/tickets/*")
                        .authenticated()

                        // Ticket oluşturma
                        .requestMatchers(HttpMethod.POST, "/api/tickets")
                        .hasRole("CUSTOMER")

                        // Ticket güncelleme
                        .requestMatchers(HttpMethod.PUT, "/api/tickets/**")
                        .hasRole("AGENT")


                        // =========================
                        // MESSAGE
                        // =========================

                        // Customer kendi mesajlarını görür
                        .requestMatchers(HttpMethod.GET, "/api/messages/my")
                        .hasRole("CUSTOMER")

                        // Agent bütün mesajları görür
                        .requestMatchers(HttpMethod.GET, "/api/messages")
                        .hasRole("AGENT")

                        // Tek mesaj görüntüleme
                        .requestMatchers(HttpMethod.GET, "/api/messages/*")
                        .authenticated()

                        // Customer + Agent mesaj gönderebilir
                        .requestMatchers(HttpMethod.POST, "/api/messages")
                        .hasAnyRole("CUSTOMER", "AGENT")

                        // Customer + Agent mesaj güncelleyebilir
                        // Kimin hangi mesajı güncelleyebileceğini Service kontrol ediyor
                        .requestMatchers(HttpMethod.PUT, "/api/messages/**")
                        .hasAnyRole("CUSTOMER", "AGENT")


                        // =========================
                        // DİĞER
                        // =========================

                        .anyRequest()
                        .authenticated()
                )

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}