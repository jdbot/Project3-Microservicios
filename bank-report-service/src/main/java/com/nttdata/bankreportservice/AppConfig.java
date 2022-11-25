package com.nttdata.bankreportservice;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * App Configuration Class.
 */
@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webC() {
        return WebClient.builder().baseUrl("http://api-gateway:8090");
    }
}
