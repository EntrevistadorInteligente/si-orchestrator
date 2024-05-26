package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.dto.MensajeValidacionMatch;
import reactor.core.publisher.Mono;


public interface OrquestadorEntrevista {

    Mono<Void> receptorInformacionEmpresa(String idEntrevista, String idInformacionEmpresaRag);

    Mono<Void> receptorHojaDeVidaMatch(MensajeValidacionMatch mensajeValidacionMatch);
}
