package com.entrevistador.orquestador.dominio.port.jms;

import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.PreparacionEntrevistaDto;
import reactor.core.publisher.Mono;

public interface JmsClient {
    Mono<Void> enviarHojaDeVida(PreparacionEntrevistaDto preparacionEntrevistaDto);

    Mono<Void> enviarInformacionEmpresa(FormularioDto formulario);

}
