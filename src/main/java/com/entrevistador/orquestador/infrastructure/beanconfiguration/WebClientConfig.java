package com.entrevistador.orquestador.infrastructure.beanconfiguration;


import com.entrevistador.orquestador.dominio.model.enums.EndpointsEnum;
import com.entrevistador.orquestador.infrastructure.properties.WebFluxProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static com.entrevistador.orquestador.dominio.model.enums.EndpointsEnum.NOTIFICADOR;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final WebFluxProperties webFluxProperties;
    @Bean
    public WebClient webClientNotification(WebClient.Builder builder) {
        return builder.baseUrl(webFluxProperties.getWebFluxRoutes()
                .get(EndpointsEnum.NOTIFICADOR.descripcion).getUrlBase()).build();
    }

    @Bean
    public WebClient webClientAnalizador(WebClient.Builder builder) {
        return builder.baseUrl(webFluxProperties.getWebFluxRoutes()
                .get(EndpointsEnum.ANALIZADOR.descripcion).getUrlBase()).build();
    }
}