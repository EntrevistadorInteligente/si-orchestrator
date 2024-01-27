package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface SolicitudEntrevista {

    Mono<Void> generarSolicitudEntrevista(Mono<FilePart> file, FormularioDto formularioDto);

}
