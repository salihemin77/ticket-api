

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

                        // Yeni kullanıcı kayıt olabilir
                        .requestMatchers(HttpMethod.POST, "/api/users")
                        .permitAll()

                        // Kullanıcıları görüntüleme
                        .requestMatchers(HttpMethod.GET, "/api/users/**")
                        .authenticated()

                        // Kullanıcı güncelleme
                        .requestMatchers(HttpMethod.PUT, "/api/users/**")
                        .hasRole("AGENT")

                        // Kullanıcı silme
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**")
                        .hasRole("AGENT")


                        // =========================
                        // TICKET
                        // =========================

                        // Ticket görüntüleme
                        .requestMatchers(HttpMethod.GET, "/api/tickets/**")
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

                        // Mesajları görüntüleme
                        .requestMatchers(HttpMethod.GET, "/api/messages/**")
                        .authenticated()

                        // Mesaj gönderme
                        .requestMatchers(HttpMethod.POST, "/api/messages")
                        .hasAnyRole("CUSTOMER", "AGENT")

                        // Mesaj güncelleme
                        .requestMatchers(HttpMethod.PUT, "/api/messages/**")
                        .hasAnyRole("CUSTOMER", "AGENT")


                        // =========================
                        // GERİ KALANLAR
                        // =========================

                        .anyRequest()
                        .authenticated()
                )

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}