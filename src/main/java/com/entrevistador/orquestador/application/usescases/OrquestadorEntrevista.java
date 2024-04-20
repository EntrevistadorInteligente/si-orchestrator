package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.MensajeValidacionMatch;
import reactor.core.publisher.Mono;

import java.util.List;

public interface OrquestadorEntrevista {

    Mono<Void> receptorInformacionEmpresa(String idEntrevista, String idInformacionEmpresaRag);

    Mono<Void> enviarPreguntasFront(List<String> preguntas);

    Mono<Void> receptorHojaDeVidaMatch(MensajeValidacionMatch mensajeValidacionMatch);
}
