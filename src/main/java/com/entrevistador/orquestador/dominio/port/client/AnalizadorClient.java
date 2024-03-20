package com.entrevistador.orquestador.dominio.port.client;

import com.entrevistador.orquestador.dominio.model.dto.PerfilEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.PreparacionEntrevistaDto;
import reactor.core.publisher.Mono;

public interface AnalizadorClient {

    Mono<Void> enviarHojaDeVida(PreparacionEntrevistaDto preparacionEntrevistaDto);
    Mono<Void> enviarInformacionEmpresa(PerfilEntrevistaDto perfil);
}
