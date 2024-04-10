package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.VistaPreviaEntrevistaDto;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.util.List;

public interface HojaDeVida {
    Mono<Void> generarSolicitudHojaDeVida(Mono<FilePart> file, String username);

    Mono<HojaDeVidaDto> obtenerHojaDeVida(String username);

    Mono<Void> guardarHojaDeVida(HojaDeVidaDto hojaDeVidaDto);

}
