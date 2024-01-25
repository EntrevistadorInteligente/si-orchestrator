package com.entrevistador.orquestador.dominio.port.client;

import com.entrevistador.orquestador.dominio.model.dto.PreparacionEntrevistaDto;
import reactor.core.publisher.Mono;

public interface AnalizadorClient {

    Mono<Void> enviarHojaDeVida(PreparacionEntrevistaDto preparacionEntrevistaDto);

}
