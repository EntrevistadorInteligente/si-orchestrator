package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.*;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.dominio.port.HojaDeVidaDao;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.dominio.port.jms.JmsPublisherClient;
import com.entrevistador.orquestador.dominio.service.ValidadacionEntrevistaPermitidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@Service
@RequiredArgsConstructor
public class SolicitudEntrevistaService implements SolicitudEntrevista {

    private final JmsPublisherClient jmsPublisherClient;
    private final ProcesoEntrevistaDao procesoEntrevistaDao;
    private final ValidadacionEntrevistaPermitidaService validadacionEntrevistaPermitidaService;
    private final EntrevistaDao entrevistaDao;
    private final HojaDeVidaDao hojaDeVidaDao;

    @Override
    public Mono<Void> generarSolicitudEntrevista(String username, Formulario formulario) {
        return this.validadacionEntrevistaPermitidaService.ejecutar(username)
                .then(this.hojaDeVidaDao.obtenerIdHojaDeVidaRag(username))
                .flatMap(idHojaDeVidaRag -> this.procesarHojaDeVida(idHojaDeVidaRag,username, formulario));
    }

    @Override
    public Mono<EstadoEntrevista> obtenerEstadoEntrevistaPorUsuario(String username) {
        return entrevistaDao.obtenerEstadoEntrevistaPorUsuario(username);
    }

    @Override
    public Mono<EstadoEntrevista> obtenerEstadoEntrevistaPorId(String id) {
        return entrevistaDao.obtenerEstadoEntrevistaPorId(id);
    }

    @Override
    public Mono<Void> terminarEntrevista(String id, String feedbackUsuario) {
        return entrevistaDao.terminarEntrevista(id, feedbackUsuario);
    }

    @Override
    public Mono<EntrevistaUsuario> obtenerEntrevistaPorId(String id) {
        return this.entrevistaDao.obtenerEntrevistaPorId(id);
    }

    private Mono<Void> procesarHojaDeVida(String idHojaDeVidaRag, String username, Formulario formulario) {
        return Mono.zip(
                        this.entrevistaDao.crearEntrevistaBase(idHojaDeVidaRag, username, formulario),
                        this.procesoEntrevistaDao.crearEvento(),
                        (idEntrevista, procesoEntrevista) ->
                                this.enviarMatch(idEntrevista, idHojaDeVidaRag, procesoEntrevista, formulario)

                )
                .flatMap(tuple2Mono -> tuple2Mono.flatMap(tuple -> this.enviarInformacionEmpresa(tuple.getT1(), tuple.getT2(), formulario)));
    }

    private Mono<Tuple2<String, String>> enviarMatch(String idEntrevista, String idHojaDeVidaRag,
                                                     ProcesoEntrevista eventoEntrevista, Formulario formulario) {

        return this.jmsPublisherClient.validarmatchHojaDeVida(SolicitudMatch.builder()
                        .idEntrevista(idEntrevista)
                        .idHojaDeVidaRag(idHojaDeVidaRag)
                        .formulario(formulario)
                        .build())
                .then(Mono.just(Tuples.of(idEntrevista, eventoEntrevista.getUuid())));
    }

    private Mono<Void> enviarInformacionEmpresa(String idEntrevista, String idEventoEntrevista, Formulario formulario) {
        return this.jmsPublisherClient.enviarInformacionEmpresa(PosicionEntrevista.builder()
                .eventoEntrevistaId(idEventoEntrevista)
                .idEntrevista(idEntrevista)
                .formulario(InformacionEmpresa.builder()
                        .empresa(formulario.getEmpresa())
                        .pais(formulario.getPais())
                        .seniority(formulario.getSeniority())
                        .perfil(formulario.getPerfil())
                        .descripcionVacante(formulario.getDescripcionVacante())
                        .build())
                .build());
    }

}

