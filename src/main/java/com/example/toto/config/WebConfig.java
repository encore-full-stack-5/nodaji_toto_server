package com.example.toto.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final String frontAddr;
    private final String paymentAddr;

    public WebConfig(
            @Value("${address.front}") String frontAddr,
            @Value("${address.payment}") String paymentAddr
    ) {
        this.frontAddr = frontAddr;
        this.paymentAddr = paymentAddr;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Authorization", "content-type")
                .exposedHeaders("Custom-Header")
                .maxAge(5000);
    }
}