package com.entrevistador.orquestador.application.usescases;


import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;

import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface OrquestadorEntrevista {
    Mono<Void> receptorHojaDeVida(String idEntrevista, HojaDeVidaDto resume, RagsIdsDto ragsIdsDto);

    Mono<Void> receptorInformacionEmpresa(String idEntrevista, InformacionEmpresaDto info, RagsIdsDto ragsIdsDto);

    void generarEntrevistaConDatosDummy(String idEntrevista, RagsIdsDto ragsIdsDto);

    Mono<Void> enviarPreguntasFront(List<String> preguntas);
}
