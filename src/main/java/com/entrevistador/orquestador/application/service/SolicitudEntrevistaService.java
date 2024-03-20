package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.PerfilEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.PreparacionEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.dominio.port.client.AnalizadorClient;
import com.entrevistador.orquestador.dominio.port.client.RecopiladorEmpresaClient;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorPdfService;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Objects;

@Service
public class SolicitudEntrevistaService implements SolicitudEntrevista {

    private final AnalizadorClient analizadorClient;
    private final ValidadorPdfService validadorPdfService;
    private final ProcesoEntrevistaDao procesoEntrevistaDao;
    private final CrearEntrevistaService crearEntrevistaService;
    private final RecopiladorEmpresaClient recopiladorEmpresaClient;

    public SolicitudEntrevistaService(AnalizadorClient analizadorClient, ValidadorPdfService validadorPdfService, ProcesoEntrevistaDao procesoEntrevistaDao, CrearEntrevistaService crearEntrevistaService, RecopiladorEmpresaClient recopiladorEmpresaClient) {
        this.analizadorClient = analizadorClient;
        this.validadorPdfService = validadorPdfService;
        this.procesoEntrevistaDao = procesoEntrevistaDao;
        this.crearEntrevistaService = crearEntrevistaService;
        this.recopiladorEmpresaClient = recopiladorEmpresaClient;
    }

    public Mono<Void> generarSolicitudEntrevista(Mono<FilePart> file, FormularioDto formulario) {
        return file.flatMap(this.validadorPdfService::ejecutar)
                .flatMap(bytes -> this.procesarHojaDeVida(bytes, formulario));
    }

    private Mono<Tuple2<String, String>> enviarHojaDeVida(String idEntrevista, ProcesoEntrevistaDto eventoEntrevista, byte[] hojaDeVidaBytes) {
        return this.analizadorClient.enviarHojaDeVida(
                        PreparacionEntrevistaDto.builder()
                                .idEntrevista(idEntrevista)
                                .eventoEntrevistaId(eventoEntrevista.getUuid())
                                .hojaDeVida(hojaDeVidaBytes)
                                .build())
                .then(Mono.just(Tuples.of(idEntrevista, eventoEntrevista.getUuid())));
    }

    private Mono<Void> procesarHojaDeVida(byte[] hojaDeVidaBytes, FormularioDto formulario) {
        return this.crearEntrevistaService.ejecutar()
                .zipWith(this.procesoEntrevistaDao.crearEvento(), (idEntrevista, eventoEntrevista) ->
                        this.enviarHojaDeVida(idEntrevista, eventoEntrevista, hojaDeVidaBytes))
                .flatMap(result -> {
                    String idEntrevista = Objects.requireNonNull(result.block()).getT1();
                    String idEventoEntrevista = Objects.requireNonNull(result.block()).getT2();
                    return this.enviarInformacionEmpresa(idEntrevista, idEventoEntrevista, formulario);
                });
    }


    private Mono<Void> enviarInformacionEmpresa(String idEntrevista, String idEventoEntrevista,FormularioDto formulario) {
        return this.analizadorClient.enviarInformacionEmpresa(PerfilEntrevistaDto.builder()
                .eventoEntrevistaId(idEventoEntrevista)
                .idEntrevista(idEntrevista)
                .formulario(formulario)
                .build());
    }

}

