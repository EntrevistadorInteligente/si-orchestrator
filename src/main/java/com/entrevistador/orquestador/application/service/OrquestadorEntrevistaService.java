package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.OrquestadorEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.SolicitudGeneracionEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.client.AnalizadorClient;
import com.entrevistador.orquestador.dominio.port.sse.SseService;
import com.entrevistador.orquestador.dominio.service.ActualizarInformacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.SolicitudPreparacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorEventosSimultaneosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrquestadorEntrevistaService implements OrquestadorEntrevista {

    private final ActualizarInformacionEntrevistaService actualizarInformacionEntrevistaService;
    private final ValidadorEventosSimultaneosService validadorEventosSimultaneosService;
    private final AnalizadorClient analizadorClient;
    private final SseService sseService;

    @Override
    public Mono<Void> receptorHojaDeVida(String idEntrevista, HojaDeVidaDto resume) {
        log.info("Recibiendo informacion hoja de vida");
        return this.actualizarInformacionEntrevistaService
                .actualizarHojaDeVida(idEntrevista, resume)
                .flatMap(this.validadorEventosSimultaneosService::ejecutar)
                .flatMap(aBoolean -> enviarInformacionEntrevistaAPreparador(aBoolean, idEntrevista));
    }

    @Override
    public Mono<Void> receptorInformacionEmpresa(String idEntrevista, InformacionEmpresaDto info) {
        log.info("Recibiendo informacion empresa");
        return this.actualizarInformacionEntrevistaService
                .actualizarInformacionEmpresa(idEntrevista, info)
                .flatMap(this.validadorEventosSimultaneosService::ejecutar)
                .flatMap(aBoolean -> enviarInformacionEntrevistaAPreparador(aBoolean, idEntrevista));
    }

    @Override
    public void generarEntrevistaConDatosDummy(String idEntrevista) {
        enviarInformacionEntrevistaAPreparador(true, idEntrevista);
    }

    @Override
    public Mono<Void> enviarPreguntasFront(List<String> preguntas) {
        return sseService.emitEvent(ServerSentEvent.<String>builder()
                .data(preguntas.toString())
                .build());
    }

    private Mono<Void> enviarInformacionEntrevistaAPreparador(boolean eventosFinalizados, String idEntrevista) {
        if (eventosFinalizados) {
            return this.analizadorClient.generarEntrevista(SolicitudGeneracionEntrevistaDto.builder()
                            .idEntrevista(idEntrevista)
                            .idHojaDeVida("660a01be8e40789a68524680") // TODO se deben recuperar los ids de los rags
                            .idInformacionEmpresa("660ccd644cb041fd51d1ed3f")
                            .build())
                    .then();
        }
        return Mono.empty();
    }
}

