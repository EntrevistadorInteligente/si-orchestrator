package com.entrevistador.orquestador.dominio.port.client;

import com.entrevistador.orquestador.dominio.model.dto.*;
import com.entrevistador.orquestador.dominio.model.dto.SolicitudHojaDeVidaDto;
import reactor.core.publisher.Mono;

public interface AnalizadorClient {

    Mono<Void> enviarHojaDeVida(SolicitudHojaDeVidaDto solicitudHojaDeVidaDto);

    Mono<Void> enviarInformacionEmpresa(PosicionEntrevistaDto perfil);

    Mono<Void> generarEntrevista(SolicitudGeneracionEntrevistaDto solicitudGeneracionEntrevista);
    Mono<Void> validarmatchHojaDeVida(SolicitudMatchDto solicitudMatchDto);
}
