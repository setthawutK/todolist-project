package com.example.WORKPLS; 

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")   // ใช้ allowedOriginPatterns แทน allowedOrigins
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}