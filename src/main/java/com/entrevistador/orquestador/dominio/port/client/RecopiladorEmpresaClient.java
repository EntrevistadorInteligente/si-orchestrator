package com.entrevistador.orquestador.dominio.port.client;

import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import reactor.core.publisher.Mono;

public interface RecopiladorEmpresaClient {

    Mono<Void> enviarInformacionEmpresa(FormularioDto formulario);

}
