package com.mcart.user_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                  // Applies to all endpoints
                .allowedOrigins("*")                // Allows all origins
                .allowedMethods("*")                // Allows all HTTP methods
                .allowedHeaders("*")                // Allows all headers
                .allowCredentials(false);           // No cookies/sessions sent
    }
}
