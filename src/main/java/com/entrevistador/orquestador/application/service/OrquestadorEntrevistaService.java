package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.OrquestadorEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.MensajeValidacionMatch;
import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import com.entrevistador.orquestador.dominio.model.dto.SolicitudGeneracionEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.dominio.port.jms.JmsPublisherClient;
import com.entrevistador.orquestador.dominio.port.sse.SseService;
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

    private final JmsPublisherClient jmsPublisherClient;
    private final SseService sseService;
    private final EntrevistaDao entrevistaDao;
    private final ValidadorEventosSimultaneosService validadorEventosSimultaneosService;

    @Override
    public Mono<Void> receptorInformacionEmpresa(String idEntrevista, String idInformacionEmpresaRag) {
        log.info("Recibiendo informacion empresa");
        return this.entrevistaDao.actualizarIdInformacionEmpresaRag(idEntrevista, idInformacionEmpresaRag)
                .then(this.validadorEventosSimultaneosService.ejecutar(idEntrevista))
                .flatMap(ragsIdsDto -> enviarInformacionEntrevistaAPreparador(ragsIdsDto, idEntrevista));
    }

    @Override
    public Mono<Void> enviarPreguntasFront(List<String> preguntas) {
        return sseService.emitEvent(ServerSentEvent.<String>builder()
                .data(preguntas.toString())
                .build());
    }

    @Override
    public Mono<Void> receptorHojaDeVidaMatch(MensajeValidacionMatch mensajeValidacionMatch) {
        log.info("Recibiendo informacion validacion hoja de vida");
        return this.entrevistaDao.actualizarEstadoEntrevista(mensajeValidacionMatch.getIdEntrevista(), mensajeValidacionMatch.isMatchValido())
                .then(this.validadorEventosSimultaneosService.ejecutar(mensajeValidacionMatch.getIdEntrevista()))
                .flatMap(ragsIdsDto -> enviarInformacionEntrevistaAPreparador(ragsIdsDto, mensajeValidacionMatch.getIdEntrevista()));
    }

    private Mono<Void> enviarInformacionEntrevistaAPreparador(RagsIdsDto ragsIdsDto, String idEntrevista) {

        if(ragsIdsDto != null){
            return this.jmsPublisherClient.generarEntrevista(SolicitudGeneracionEntrevistaDto.builder()
                            .idEntrevista(idEntrevista)
                            .idHojaDeVida(ragsIdsDto.getIdHojaDeVidaRag())
                            .idInformacionEmpresa(ragsIdsDto.getIdInformacionEmpresaRag())
                            .build())
                    .then();
        }

        return Mono.empty();

    }
}

