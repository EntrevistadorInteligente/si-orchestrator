package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.dto.EntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.FeedbackDto;
import com.entrevistador.orquestador.dominio.model.dto.MensajeValidacionMatch;
import reactor.core.publisher.Mono;


public interface OrquestadorEntrevista {

    Mono<Void> receptorInformacionEmpresa(String idEntrevista, String idInformacionEmpresaRag);
    Mono<Void> receptorHojaDeVidaMatch(MensajeValidacionMatch mensajeValidacionMatch);
    Mono<Void> actualizarEstadoEntrevistaPorPreguntas(EntrevistaDto entrevista);
    Mono<Void> actualizarEstadoEntrevistaPorFeedback(FeedbackDto feedback);

}
