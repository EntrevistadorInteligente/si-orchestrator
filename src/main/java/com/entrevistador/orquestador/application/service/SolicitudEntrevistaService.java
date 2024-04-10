package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.PosicionEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.VistaPreviaEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.dominio.port.client.AnalizadorClient;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorPdfService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
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
                                Mono.just(Tuples.of(idEntrevista, procesoEntrevistaDto.getUuid())) //TODO: reemplazar por validar match

                )
                .flatMap(tuple2Mono -> tuple2Mono.flatMap(tuple -> this.enviarInformacionEmpresa(tuple.getT1(), tuple.getT2(), formulario)));
    }

    private Mono<Void> enviarInformacionEmpresa(String idEntrevista, String idEventoEntrevista, FormularioDto formulario) {
        return this.analizadorClient.enviarInformacionEmpresa(PosicionEntrevistaDto.builder()
                .eventoEntrevistaId(idEventoEntrevista)
                .idEntrevista(idEntrevista)
                .formulario(formulario)
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

