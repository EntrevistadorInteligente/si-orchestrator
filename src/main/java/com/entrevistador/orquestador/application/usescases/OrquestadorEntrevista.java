package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface OrquestadorEntrevista {
    Mono<Void> receptorHojaDeVida(String idEntrevista, HojaDeVidaDto resume);

    Mono<Void> receptorInformacionEmpresa(String idEntrevista, FormularioDto info, List<String> preguntas);

    void generarEntrevistaConDatosDummy(String idEntrevista);
}
