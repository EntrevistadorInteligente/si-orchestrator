package com.entrevistador.orquestador.infrastructure.adapter.client;

import com.entrevistador.orquestador.dominio.model.HojaDeVidaModel;
import com.entrevistador.orquestador.dominio.model.SolicitudHojaDeVida;
import com.entrevistador.orquestador.dominio.port.client.AnalizadorClient;
import com.entrevistador.orquestador.infrastructure.adapter.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.EntrevistaMapper;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.HojaDeVidaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class AnalizadorRestClient implements AnalizadorClient {

    @Qualifier("webClientAnalizador")
    private final WebClient webClient;
    private final HojaDeVidaMapper mapper;
    private final EntrevistaMapper mapperEntrevista;

    @Override
    public Mono<HojaDeVidaModel> enviar(SolicitudHojaDeVida solicitudHojaDeVida) {

        return this.webClient.post()
                .uri("/v1/analizador/hojas-de-vida/generar")
                .bodyValue(this.mapperEntrevista.mapSolicitudHojaDeVidaSolicitudHojaDeVidaDto(solicitudHojaDeVida))
                .accept(MediaType. APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(error -> {
                                    log.error("Error al enviar la hoja de vida al analizador: {}", error);
                                    return Mono.error(new RuntimeException(error));
                                }))
                .bodyToMono(HojaDeVidaDto.class)
                .flatMap(hojaDeVidaDto -> Mono.just(this.mapper.mapHojaDeVidaDtoToHojaDeVida(hojaDeVidaDto)));
    }
}