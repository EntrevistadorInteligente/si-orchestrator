package com.entrevistador.orquestador.infrastructure.adapter.jms;

import com.entrevistador.orquestador.dominio.model.PosicionEntrevista;
import com.entrevistador.orquestador.dominio.model.SolicitudGeneracionEntrevista;
import com.entrevistador.orquestador.dominio.model.SolicitudHojaDeVida;
import com.entrevistador.orquestador.dominio.model.SolicitudMatch;
import com.entrevistador.orquestador.dominio.port.jms.JmsPublisherClient;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.EntrevistaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public final class JmsPublisherAdapter implements JmsPublisherClient {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final EntrevistaMapper mapper;
    private static final String SENT_MESSAGE = "Sent message=[";
    private static final String WITH_OFFSET = "] with offset=[";
    private static final String UNABLE_SEND_MESSAGE = "Unable to send message=[";
    private static final String DUE_TO = "] due to : ";
    private static final String ERROR = "ERROR : ";

    @Value("${kafka.topic-analizador-publisher}")
    private String hojaDeVidaPublisherTopic;

    @Value("${kafka.topic-recopilador-publisher}")
    private String topicEmpresa;

    @Value("${kafka.topic-generador-publisher}")
    private String topicGenerador;

    @Value("${kafka.topic-analizador-validador-publisher}")
    private String topicValidador;

    @Override
    public Mono<Void> enviarHojaDeVida(SolicitudHojaDeVida solicitudHojaDeVida) {
        return Mono.just(this.mapper.mapSolicitudHojaDeVidaSolicitudHojaDeVidaDto(solicitudHojaDeVida))
                .flatMap(solicitudHojaDeVidaDto ->
                        enviarMensaje(hojaDeVidaPublisherTopic, solicitudHojaDeVidaDto, solicitudHojaDeVidaDto.getUsername()));
    }

    @Override
    public Mono<Void> enviarInformacionEmpresa(PosicionEntrevista posicionEntrevista) {
        return Mono.just(this.mapper.mapPosicionEntrevistaToPosicionEntrevistaDto(posicionEntrevista))
                .flatMap(posicionEntrevistaDto ->
                        enviarMensaje(topicEmpresa, posicionEntrevistaDto, posicionEntrevistaDto.getIdEntrevista()));
    }

    @Override
    public Mono<Void> generarEntrevista(SolicitudGeneracionEntrevista generacionEntrevista) {
        return Mono.just(this.mapper.mapSolicitudGeneracionEntrevistaToSolicitudGeneracionEntrevistaDto(generacionEntrevista))
                .flatMap(solicitudGeneracionEntrevistaDto ->
                        enviarMensaje(topicGenerador, solicitudGeneracionEntrevistaDto, solicitudGeneracionEntrevistaDto.getIdEntrevista()));
    }

    @Override
    public Mono<Void> validarmatchHojaDeVida(SolicitudMatch solicitudMatch) {
        return Mono.just(this.mapper.mapSolicitudMatchToSolicitudMatchDto(solicitudMatch))
                .flatMap(solicitudMatchDto ->
                        enviarMensaje(topicValidador, solicitudMatchDto, solicitudMatchDto.getIdEntrevista()));
    }

    private <T> Mono<Void> enviarMensaje(String topic, T mensaje, String identificador) {
        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, mensaje);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info(SENT_MESSAGE + "{}" + WITH_OFFSET + "{}]", identificador, result.getRecordMetadata().offset());
                } else {
                    log.info(UNABLE_SEND_MESSAGE + "{}" + DUE_TO + "{}", mensaje, ex.getMessage());
                }
            });

        } catch (Exception ex) {
            log.info(ERROR + "{}", ex.getMessage());
        }

        return Mono.empty();
    }
}
