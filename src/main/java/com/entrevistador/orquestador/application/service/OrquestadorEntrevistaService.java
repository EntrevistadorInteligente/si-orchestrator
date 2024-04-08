package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.OrquestadorEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import com.entrevistador.orquestador.dominio.model.dto.SolicitudGeneracionEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.dominio.port.client.AnalizadorClient;
import com.entrevistador.orquestador.dominio.port.sse.SseService;
import com.entrevistador.orquestador.dominio.service.ActualizarInformacionEntrevistaService;
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
    private final AnalizadorClient analizadorClient;
    private final SseService sseService;
    private final EntrevistaDao entrevistaDao;

    @Override
    public Mono<Void> receptorHojaDeVida(String idEntrevista, HojaDeVidaDto resume) {
        log.info("Recibiendo informacion hoja de vida");
        return this.actualizarInformacionEntrevistaService
                .actualizarHojaDeVida(idEntrevista, resume)
                .flatMap(ragsIdDto ->  this.entrevistaDao.consultarRagsId(idEntrevista))
                .flatMap(ragsIdDto -> enviarInformacionEntrevistaAPreparador(idEntrevista, ragsIdDto));
    }

    @Override
    public Mono<Void> receptorInformacionEmpresa(String idEntrevista, InformacionEmpresaDto info) {
        log.info("Recibiendo informacion empresa");
        return this.actualizarInformacionEntrevistaService
                .actualizarInformacionEmpresa(idEntrevista, info)
                .flatMap(ragsIdDto -> this.entrevistaDao.consultarRagsId(idEntrevista))
                .flatMap(ragsIdDto -> enviarInformacionEntrevistaAPreparador(idEntrevista, ragsIdDto));
    }

    @Override
    public void generarEntrevistaConDatosDummy(String idEntrevista, RagsIdsDto ragsIdsDto) {
        enviarInformacionEntrevistaAPreparador(idEntrevista, ragsIdsDto);
    }

    @Override
    public Mono<Void> enviarPreguntasFront(List<String> preguntas) {
        return sseService.emitEvent(ServerSentEvent.<String>builder()
                .data(preguntas.toString())
                .build());
    }

    private Mono<Void> enviarInformacionEntrevistaAPreparador(String idEntrevista, RagsIdsDto ragsIdsDto) {
        if (ragsIdsDto.getidHojaDeVidaRag() != null && ragsIdsDto.getidInformacionEmpresaRag() != null) {
            return this.analizadorClient.generarEntrevista(SolicitudGeneracionEntrevistaDto.builder()
                            .idEntrevista(idEntrevista)
                            .idHojaDeVida(ragsIdsDto.getidHojaDeVidaRag())
                            .idInformacionEmpresa(ragsIdsDto.getidInformacionEmpresaRag())
                            .build())
                    .then();
        }
        return Mono.empty();
    }
}

