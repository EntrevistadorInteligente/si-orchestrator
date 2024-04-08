package com.entrevistador.orquestador.application.usescases;


import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;

import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface OrquestadorEntrevista {
    Mono<Void> receptorHojaDeVida(String idEntrevista, HojaDeVidaDto resume);

    Mono<Void> receptorInformacionEmpresa(String idEntrevista, InformacionEmpresaDto info);

    void generarEntrevistaConDatosDummy(String idEntrevistao, RagsIdsDto ragsIdsDto);

    Mono<Void> enviarPreguntasFront(List<String> preguntas);
}
