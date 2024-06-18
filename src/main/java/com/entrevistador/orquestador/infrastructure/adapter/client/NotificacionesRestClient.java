package com.entrevistador.orquestador.infrastructure.adapter.client;

import com.entrevistador.orquestador.dominio.model.dto.NotifiacionDto;
import com.entrevistador.orquestador.dominio.port.client.NotificacionesClient;
import com.entrevistador.orquestador.infrastructure.properties.WebFluxProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.entrevistador.orquestador.dominio.model.enums.EndpointNotificacionesEnum.ENVIAR_EVENTO;
import static com.entrevistador.orquestador.dominio.model.enums.EndpointsEnum.NOTIFICADOR;

@Component
@Slf4j
public class NotificacionesRestClient implements NotificacionesClient {
    private final WebClient webClient;
    private final WebFluxProperties webFluxProperties;

    public NotificacionesRestClient(WebClient.Builder webClientBuilder, WebFluxProperties webFluxProperties) {
        this.webClient = webClientBuilder.baseUrl(webFluxProperties.getWebFluxRoutes().get(NOTIFICADOR.descripcion).getUrlBase()).build();
        this.webFluxProperties = webFluxProperties;
    }

    @Override
    public Mono<Void> enviar(String userId, NotifiacionDto notifiacionDto) {
        return this.webClient.post()
                .uri(webFluxProperties.getWebFluxRoutes()
                        .get(NOTIFICADOR.descripcion)
                        .getEndpoints().get(ENVIAR_EVENTO.descripcion).concat(userId))
                .bodyValue(notifiacionDto)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
