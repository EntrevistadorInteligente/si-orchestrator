package com.entrevistador.orquestador.dominio.port.jms;

import com.entrevistador.orquestador.application.dto.SolicitudGeneracionEntrevistaDto;
import com.entrevistador.orquestador.application.dto.SolicitudHojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.PosicionEntrevista;
import com.entrevistador.orquestador.dominio.model.SolicitudMatch;
import reactor.core.publisher.Mono;

public interface JmsPublisherClient {

    Mono<Void> enviarHojaDeVida(SolicitudHojaDeVidaDto solicitudHojaDeVidaDto);

    Mono<Void> enviarInformacionEmpresa(PosicionEntrevista posicionEntrevista);

    Mono<Void> generarEntrevista(SolicitudGeneracionEntrevistaDto solicitudGeneracionEntrevista);

    Mono<Void> validarmatchHojaDeVida(SolicitudMatch solicitudMatch);

}
