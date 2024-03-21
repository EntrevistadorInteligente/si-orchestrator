package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.PerfilEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.PreparacionEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.dominio.port.client.AnalizadorClient;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@Service
@RequiredArgsConstructor
public class SolicitudEntrevistaService implements SolicitudEntrevista {

    private final AnalizadorClient analizadorClient;
    private final ValidadorPdfService validadorPdfService;
    private final ProcesoEntrevistaDao procesoEntrevistaDao;
    private final CrearEntrevistaService crearEntrevistaService;

    public Mono<Void> generarSolicitudEntrevista(Mono<FilePart> file, FormularioDto formulario) {
        return file.flatMap(this.validadorPdfService::ejecutar)
                .flatMap(bytes -> this.procesarHojaDeVida(bytes, formulario));
    }

    private Mono<Void> procesarHojaDeVida(byte[] hojaDeVidaBytes, FormularioDto formulario) {
        return Mono.zip(
                        this.crearEntrevistaService.ejecutar(),
                        this.procesoEntrevistaDao.crearEvento(),
                        (idEntrevista, procesoEntrevistaDto) ->
                                this.enviarHojaDeVida(idEntrevista, procesoEntrevistaDto, hojaDeVidaBytes)
                )
                .flatMap(tuple2Mono -> tuple2Mono.flatMap(tuple -> this.enviarInformacionEmpresa(tuple.getT1(), tuple.getT2(), formulario)));
    }

    private Mono<Tuple2<String, String>> enviarHojaDeVida(String idEntrevista, ProcesoEntrevistaDto eventoEntrevista, byte[] hojaDeVidaBytes) {
        return this.analizadorClient.enviarHojaDeVida(
                        PreparacionEntrevistaDto.builder()
                                .idEntrevista(idEntrevista)
                                .eventoEntrevistaId(eventoEntrevista.getUuid())
                                .hojaDeVida(hojaDeVidaBytes)
                                .build())
                .thenReturn(Tuples.of(idEntrevista, eventoEntrevista.getUuid()));
    }


    private Mono<Void> enviarInformacionEmpresa(String idEntrevista, String idEventoEntrevista, FormularioDto formulario) {
        return this.analizadorClient.enviarInformacionEmpresa(PerfilEntrevistaDto.builder()
                .eventoEntrevistaId(idEventoEntrevista)
                .idEntrevista(idEntrevista)
                .formulario(formulario)
                .build());
    }

}

