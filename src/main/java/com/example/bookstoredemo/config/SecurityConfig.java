package com.example.bookstoredemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
// @EnableWebSecurity // Annotation này không bắt buộc ở Spring Boot 3+ nếu có spring-boot-starter-security
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults()) // Kích hoạt CORS với cấu hình bên dưới
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Cấu hình cho API stateless
                .authorizeHttpRequests(auth -> auth
                        // --- CÁC ENDPOINT CÔNG KHAI ---
                        .requestMatchers("/api/**").permitAll()
                        // Thêm các endpoint công khai khác ở đây (ví dụ: GET books)

                        // --- CÁC ENDPOINT CẦN XÁC THỰC ---
                        .anyRequest().authenticated()
                );
                // .httpBasic(Customizer.withDefaults()); // Không cần httpBasic cho API dùng JWT

        // TODO: Thêm JwtAuthFilter của bạn vào đây khi đã tạo xong
        // http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://ManhDucLai.github.io/bookstoredemo","http://localhost:5173")); // domain FE
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
