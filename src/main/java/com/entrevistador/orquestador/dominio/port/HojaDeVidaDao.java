package com.entrevistador.orquestador.dominio.port;

import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import reactor.core.publisher.Mono;

public interface HojaDeVidaDao {
    Mono<Void> guardarHojaDeVida(HojaDeVidaDto hojaDeVidaDto);
    Mono<HojaDeVidaDto> obtenerHojaDeVida(String username);
    Mono<String> obtenerIdHojaDeVidaRag(String nombre);
}
