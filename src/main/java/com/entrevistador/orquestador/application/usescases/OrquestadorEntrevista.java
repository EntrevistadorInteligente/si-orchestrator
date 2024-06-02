package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.EntrevistaModel;
import com.entrevistador.orquestador.dominio.model.Feedback;
import com.entrevistador.orquestador.dominio.model.MensajeValidacionMatch;
import reactor.core.publisher.Mono;


public interface OrquestadorEntrevista {

    Mono<Void> receptorInformacionEmpresa(String idEntrevista, String idInformacionEmpresaRag);
    Mono<Void> receptorHojaDeVidaMatch(MensajeValidacionMatch mensajeValidacionMatch);
    Mono<Void> actualizarEstadoEntrevistaPorPreguntas(EntrevistaModel entrevistaModel);
    Mono<Void> actualizarEstadoEntrevistaPorFeedback(Feedback feedback);

}
