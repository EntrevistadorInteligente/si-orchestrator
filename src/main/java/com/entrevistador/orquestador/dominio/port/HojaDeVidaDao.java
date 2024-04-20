package com.entrevistador.orquestador.dominio.port;

import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.PerfilDto;
import reactor.core.publisher.Mono;

public interface HojaDeVidaDao {
    Mono<Void> guardarHojaDeVida(HojaDeVidaDto hojaDeVidaDto);
    Mono<HojaDeVidaDto> obtenerHojaDeVidaPorNombreUsuario(String username);
    Mono<String> obtenerIdHojaDeVidaRag(String nombre);
    Mono<Void> actualizarDatosPerfil(String uuid, PerfilDto perfilDto);
}
