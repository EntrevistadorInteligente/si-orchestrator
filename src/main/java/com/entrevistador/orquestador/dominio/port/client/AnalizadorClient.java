package com.entrevistador.orquestador.dominio.port.client;

import com.entrevistador.orquestador.dominio.model.dto.PosicionEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.PreparacionEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.SolicitudGeneracionEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.SolicitudHojaDeVidaDto;
import reactor.core.publisher.Mono;

public interface AnalizadorClient {

    Mono<Void> enviarHojaDeVida(SolicitudHojaDeVidaDto solicitudHojaDeVidaDto);

    Mono<Void> enviarInformacionEmpresa(PosicionEntrevistaDto perfil);

    Mono<Void> generarEntrevista(SolicitudGeneracionEntrevistaDto solicitudGeneracionEntrevista);
}
