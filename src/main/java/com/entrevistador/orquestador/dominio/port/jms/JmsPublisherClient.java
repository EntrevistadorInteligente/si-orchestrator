package com.entrevistador.orquestador.dominio.port.jms;

import com.entrevistador.orquestador.dominio.model.PosicionEntrevista;
import com.entrevistador.orquestador.dominio.model.SolicitudGeneracionEntrevista;
import com.entrevistador.orquestador.dominio.model.SolicitudHojaDeVida;
import com.entrevistador.orquestador.dominio.model.SolicitudMatch;
import reactor.core.publisher.Mono;

public interface JmsPublisherClient {

    Mono<Void> enviarHojaDeVida(SolicitudHojaDeVida solicitudHojaDeVidaDto);

    Mono<Void> enviarInformacionEmpresa(PosicionEntrevista posicionEntrevista);

    Mono<Void> generarEntrevista(SolicitudGeneracionEntrevista generacionEntrevista);

    Mono<Void> validarmatchHojaDeVida(SolicitudMatch solicitudMatch);

}
