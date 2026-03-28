package com.example.VinylStoreManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply this rule to all of our API endpoints
                        .allowedOrigins("http://localhost:3000", "http://localhost:8081") // The ports Lovable/React usually run on
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow these actions
                        .allowedHeaders("*") // Allow all headers (including our Authorization token header)
                        .allowCredentials(true); // Allow the frontend to send the JWT token securely
            }
        };
    }
}