package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.PerfilDto;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface HojaDeVida {
    Mono<Void> generarSolicitudHojaDeVida(Mono<FilePart> file, String username);

    Mono<PerfilDto> obtenerHojaDeVida(String username);

    Mono<Void> guardarHojaDeVida(HojaDeVidaDto hojaDeVidaDto);

    Mono<Void> actualizarDatosPerfil(String uuid, PerfilDto perfilDto);
}
