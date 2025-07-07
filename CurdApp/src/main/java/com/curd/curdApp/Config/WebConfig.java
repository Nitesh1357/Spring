package com.curd.curdApp.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {  // ✅ CLASS DEFINITION ADDED

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // Vite frontend origin
                .allowedMethods("*")                     // GET, POST, PUT, DELETE etc.
                .allowedHeaders("*")                     // Accept all headers
                .allowCredentials(true);                 // For cookies or Authorization headers
    }
}
