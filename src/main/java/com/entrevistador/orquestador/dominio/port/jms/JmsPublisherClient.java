package com.entrevistador.orquestador.dominio.port.jms;

import com.entrevistador.orquestador.dominio.model.dto.PosicionEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.SolicitudGeneracionEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.SolicitudHojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.SolicitudMatchDto;
import reactor.core.publisher.Mono;

public interface JmsPublisherClient {

    Mono<Void> enviarHojaDeVida(SolicitudHojaDeVidaDto solicitudHojaDeVidaDto);

    Mono<Void> enviarInformacionEmpresa(PosicionEntrevistaDto perfil);

    Mono<Void> generarEntrevista(SolicitudGeneracionEntrevistaDto solicitudGeneracionEntrevista);

    Mono<Void> validarmatchHojaDeVida(SolicitudMatchDto solicitudMatchDto);

}
