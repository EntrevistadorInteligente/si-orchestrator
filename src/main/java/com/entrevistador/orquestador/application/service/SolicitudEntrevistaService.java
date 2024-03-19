package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.PreparacionEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.VistaPreviaEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.dominio.port.client.AnalizadorClient;
import com.entrevistador.orquestador.dominio.port.client.RecopiladorEmpresaClient;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorPdfService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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

    private Mono<Void> procesarHojaDeVida(byte[] hojaDeVidaBytes, FormularioDto formulario) {
        return this.crearEntrevistaService.ejecutar()
                .zipWith(this.procesoEntrevistaDao.crearEvento(), (idEntrevista, eventoEntrevista) ->
                        this.enviarHojaDeVida(idEntrevista, eventoEntrevista, hojaDeVidaBytes)
                )
                .flatMap(result -> this.enviarInformacionEmpresa(formulario));
    }

    private Mono<Void> enviarHojaDeVida(String idEntrevista, ProcesoEntrevistaDto eventoEntrevista, byte[] hojaDeVidaBytes) {
        return this.analizadorClient.enviarHojaDeVida(
                PreparacionEntrevistaDto.builder()
                        .idEntrevista(idEntrevista)
                        .eventoEntrevistaId(eventoEntrevista.getUuid())
                        .hojadevida(hojaDeVidaBytes)
                        .build());
    }

    private Mono<Void> enviarInformacionEmpresa(FormularioDto formulario) {
        return this.recopiladorEmpresaClient.enviarInformacionEmpresa(formulario);
    }

}

