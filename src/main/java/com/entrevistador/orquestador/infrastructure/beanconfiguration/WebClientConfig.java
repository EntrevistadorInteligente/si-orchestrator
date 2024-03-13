package com.entrevistador.orquestador.infrastructure.beanconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClientAnalizador(WebClient.Builder builder) {
        return builder.baseUrl("http://ANALIZADOR").build();
    }

    @Bean
    public WebClient webClientPreparador(WebClient.Builder builder) {
        return builder.baseUrl("http://PREPARADOR").build();
    }

    @Bean
    public WebClient webClientRecopilador(WebClient.Builder builder) {
        return builder.baseUrl("http://RECOPILADOR").build();
    }

}
