package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.PosicionEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.SolicitudMatchDto;
import com.entrevistador.orquestador.dominio.model.dto.VistaPreviaEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.dominio.port.HojaDeVidaDao;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.dominio.port.jms.JmsPublisherClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@Service
@RequiredArgsConstructor
public class SolicitudEntrevistaService implements SolicitudEntrevista {

    private final JmsPublisherClient jmsPublisherClient;
    private final ProcesoEntrevistaDao procesoEntrevistaDao;
    private final EntrevistaDao entrevistaDao;
    private final HojaDeVidaDao hojaDeVidaDao;

    @Override
    public Mono<Void> generarSolicitudEntrevista(String username, FormularioDto formulario) {
        return this.hojaDeVidaDao.obtenerIdHojaDeVidaRag(username)
                .flatMap(idHojaDeVidaRag -> this.procesarHojaDeVida(idHojaDeVidaRag,username, formulario));
    }

    private Mono<Void> procesarHojaDeVida(String idHojaDeVidaRag, String username, FormularioDto formulario) {
        return Mono.zip(
                        this.entrevistaDao.crearEntrevistaBase(idHojaDeVidaRag, username, formulario),
                        this.procesoEntrevistaDao.crearEvento(),
                        (idEntrevista, procesoEntrevistaDto) ->
                                this.enviarMatch(idEntrevista, idHojaDeVidaRag, procesoEntrevistaDto, formulario)

                )
                .flatMap(tuple2Mono -> tuple2Mono.flatMap(tuple -> this.enviarInformacionEmpresa(tuple.getT1(), tuple.getT2(), formulario)));
    }

    private Mono<Tuple2<String, String>> enviarMatch(String idEntrevista, String idHojaDeVidaRag,
                                                     ProcesoEntrevistaDto eventoEntrevista, FormularioDto formulario) {

        return this.jmsPublisherClient.validarmatchHojaDeVida(SolicitudMatchDto.builder()
                        .idEntrevista(idEntrevista)
                        .idHojaDeVidaRag(idHojaDeVidaRag)
                        .formulario(formulario)
                        .build())
                .then(Mono.just(Tuples.of(idEntrevista, eventoEntrevista.getUuid())));
    }

    private Mono<Void> enviarInformacionEmpresa(String idEntrevista, String idEventoEntrevista, FormularioDto formulario) {
        return this.jmsPublisherClient.enviarInformacionEmpresa(PosicionEntrevistaDto.builder()
                .eventoEntrevistaId(idEventoEntrevista)
                .idEntrevista(idEntrevista)
                .formulario(InformacionEmpresaDto.builder()
                        .empresa(formulario.getEmpresa())
                        .pais(formulario.getPais())
                        .seniority(formulario.getSeniority())
                        .perfil(formulario.getPerfil())
                        .descripcionVacante(formulario.getDescripcionVacante())
                        .build())
                .build());
    }

    @Override
    public List<VistaPreviaEntrevistaDto> generarPreguntas(String posicion) {

        Map<String, List<VistaPreviaEntrevistaDto>> preguntasPorPosicion = new HashMap<>();
        List<VistaPreviaEntrevistaDto> backendPreguntas = generarPreguntasBackend();
        List<VistaPreviaEntrevistaDto> frontendPreguntas = generarPreguntasFrontend();

        preguntasPorPosicion.put("FRONTEND", frontendPreguntas);
        preguntasPorPosicion.put("BACKEND", backendPreguntas);

        return preguntasPorPosicion.getOrDefault(posicion, new ArrayList<>());
    }

    private List<VistaPreviaEntrevistaDto> generarPreguntasFrontend() {
        return List.of(
                new VistaPreviaEntrevistaDto(
                        "Describe un proyecto complejo en el que hayas trabajado que incluyera tanto el desarrollo del frontend como del backend. ¿Cómo abordaste los desafíos técnicos y cómo aseguraste la cohesión entre ambas partes del proyecto?")
        );
    }

    private List<VistaPreviaEntrevistaDto> generarPreguntasBackend() {
        return List.of(
                new VistaPreviaEntrevistaDto(
                        "Describe un proyecto complejo en el que hayas trabajado que incluyera tanto el desarrollo del frontend como del backend. ¿Cómo abordaste los desafíos técnicos y cómo aseguraste la cohesión entre ambas partes del proyecto?"),
                new VistaPreviaEntrevistaDto(
                        "Describe una situación en la que hayas tenido un desacuerdo con un miembro del equipo sobre la implementación de una característica. ¿Cómo lo resolviste y qué aprendiste de esa experiencia?"),
                new VistaPreviaEntrevistaDto(
                        "El mundo del desarrollo web está constantemente evolucionando con nuevas herramientas y prácticas. ¿Cómo te mantienes actualizado con las últimas tendencias y tecnologías, y podrías compartir un ejemplo de cómo aplicaste una nueva tecnología o práctica en un proyecto reciente?"
                ));
    }
}

