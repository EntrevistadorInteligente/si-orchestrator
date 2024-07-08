package com.entrevistador.orquestador.dominio.port.client;

import com.entrevistador.orquestador.dominio.model.HojaDeVidaModel;
import com.entrevistador.orquestador.dominio.model.SolicitudHojaDeVida;
import reactor.core.publisher.Mono;

public interface AnalizadorClient {
    Mono<HojaDeVidaModel> enviar(SolicitudHojaDeVida solicitudHojaDeVida) ;
}
