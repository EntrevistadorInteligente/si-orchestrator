package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.HojaDeVidaModel;
import com.entrevistador.orquestador.dominio.model.Perfil;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface HojaDeVida {
    Mono<HojaDeVidaModel> generarSolicitudHojaDeVida(Mono<FilePart> file, String username);

    Mono<HojaDeVidaModel> obtenerHojaDeVida(String username);

    Mono<Void> guardarHojaDeVida(HojaDeVidaModel hojaDeVidaModel);

    Mono<HojaDeVidaModel> actualizarDatosPerfil(String uuid, Perfil perfil);
}
