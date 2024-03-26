package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.OrquestadorEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.port.sse.SseService;
import com.entrevistador.orquestador.dominio.service.ActualizarInformacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.SolicitudPreparacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorEventosSimultaneosService;
import com.entrevistador.orquestador.dominio.port.client.PreparadorClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrquestadorEntrevistaService implements OrquestadorEntrevista {

    private final SolicitudPreparacionEntrevistaService solicitudPreparacionEntrevistaService;
    private final ActualizarInformacionEntrevistaService actualizarInformacionEntrevistaService;
    private final ValidadorEventosSimultaneosService validadorEventosSimultaneosService;
    private final PreparadorClient preparadorClient;
    private final SseService sseService;

    public OrquestadorEntrevistaService(SolicitudPreparacionEntrevistaService solicitudPreparacionEntrevistaService, ActualizarInformacionEntrevistaService actualizarInformacionEntrevistaService, ValidadorEventosSimultaneosService validadorEventosSimultaneosService, PreparadorClient preparadorClient, SseService sseService) {
        this.solicitudPreparacionEntrevistaService = solicitudPreparacionEntrevistaService;
        this.actualizarInformacionEntrevistaService = actualizarInformacionEntrevistaService;
        this.validadorEventosSimultaneosService = validadorEventosSimultaneosService;
        this.preparadorClient = preparadorClient;
        this.sseService = sseService;
    }

    @Override
    public void receptorHojaDeVida(String idEntrevista, HojaDeVidaDto resume) {
        this.actualizarInformacionEntrevistaService.actualizarHojaDeVida(idEntrevista, resume);
        var eventosFinalizados = this.validadorEventosSimultaneosService.ejecutar(idEntrevista);
        enviarInformacionEntrevistaAPreparador(eventosFinalizados);
    }

    @Override
    public void receptorInformacionEmpresa(String idEntrevista, FormularioDto info, List<String> preguntas) {
        this.actualizarInformacionEntrevistaService.actualizarInrfomacionEmpresa(idEntrevista, info, preguntas);
        var eventosFinalizados = this.validadorEventosSimultaneosService.ejecutar(idEntrevista);
        enviarInformacionEntrevistaAPreparador(eventosFinalizados);
    }

    @Override
    public void generarEntrevistaConDatosDummy(String idEntrevista){
        enviarInformacionEntrevistaAPreparador(true);
    }

    private void enviarInformacionEntrevistaAPreparador(boolean eventosFinalizados){
        if(eventosFinalizados){
            log.info("Entrevista lista");
            this.sseService.emitEvent(ServerSentEvent.<String>builder()
                    .data("La entrevista está lista.")
                    .build());
            this.solicitudPreparacionEntrevistaService.ejecutar();
            this.preparadorClient.generarEntrevista();
        }

    }

}

