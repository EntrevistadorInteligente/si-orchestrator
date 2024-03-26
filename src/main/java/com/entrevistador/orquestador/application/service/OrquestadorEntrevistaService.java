package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.OrquestadorEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.service.ActualizarInformacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.SolicitudPreparacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorEventosSimultaneosService;
import com.entrevistador.orquestador.dominio.port.client.PreparadorClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


import java.util.List;

@Service
public class OrquestadorEntrevistaService implements OrquestadorEntrevista {

    private final SolicitudPreparacionEntrevistaService solicitudPreparacionEntrevistaService;
    private final ActualizarInformacionEntrevistaService actualizarInformacionEntrevistaService;
    private final ValidadorEventosSimultaneosService validadorEventosSimultaneosService;
    private final PreparadorClient preparadorClient;

    public OrquestadorEntrevistaService(SolicitudPreparacionEntrevistaService solicitudPreparacionEntrevistaService, ActualizarInformacionEntrevistaService actualizarInformacionEntrevistaService, ValidadorEventosSimultaneosService validadorEventosSimultaneosService, PreparadorClient preparadorClient) {
        this.solicitudPreparacionEntrevistaService = solicitudPreparacionEntrevistaService;
        this.actualizarInformacionEntrevistaService = actualizarInformacionEntrevistaService;
        this.validadorEventosSimultaneosService = validadorEventosSimultaneosService;
        this.preparadorClient = preparadorClient;
    }

    @Override
    public Mono<Void> receptorHojaDeVida(String idEntrevista, HojaDeVidaDto resume) {
        return this.actualizarInformacionEntrevistaService.actualizarHojaDeVida(idEntrevista, resume).flatMap(data ->
                this.validadorEventosSimultaneosService.ejecutar(data)).flatMap(this::enviarInformacionEntrevistaAPreparador);
    }

    @Override
    public Mono<Void> receptorInformacionEmpresa(String idEntrevista, FormularioDto info, List<String> preguntas) {
        return this.actualizarInformacionEntrevistaService.actualizarInformacionEmpresa(idEntrevista, info, preguntas).flatMap(data ->
                this.validadorEventosSimultaneosService.ejecutar(data)).flatMap(this::enviarInformacionEntrevistaAPreparador);
    }

    @Override
    public void generarEntrevistaConDatosDummy(String idEntrevista){
        enviarInformacionEntrevistaAPreparador(true);
    }

    private Mono<Void> enviarInformacionEntrevistaAPreparador(boolean eventosFinalizados){
        if(eventosFinalizados){
            this.solicitudPreparacionEntrevistaService.ejecutar();
            this.preparadorClient.generarEntrevista();
        }
        return Mono.empty();
    }

}

