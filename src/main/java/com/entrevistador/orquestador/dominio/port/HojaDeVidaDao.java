package com.entrevistador.orquestador.dominio.port;

import com.entrevistador.orquestador.dominio.model.HojaDeVidaModel;
import com.entrevistador.orquestador.dominio.model.Perfil;
import reactor.core.publisher.Mono;

public interface HojaDeVidaDao {
    Mono<HojaDeVidaModel> guardarHojaDeVida(HojaDeVidaModel hojaDeVidaModel);
    Mono<HojaDeVidaModel> obtenerHojaDeVidaPorNombreUsuario(String username);
    Mono<String> obtenerIdHojaDeVidaRag(String nombre);
    Mono<Void> actualizarDatosPerfil(String uuid, Perfil perfil);
}
