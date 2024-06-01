package com.entrevistador.orquestador.infrastructure.adapter.jms;

import com.entrevistador.orquestador.dominio.model.dto.*;
import com.entrevistador.orquestador.dominio.port.jms.JmsPublisherClient;
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
    public Mono<Void> enviarHojaDeVida(SolicitudHojaDeVidaDto solicitudHojaDeVidaDto) {
        return enviarMensaje(hojaDeVidaPublisherTopic, solicitudHojaDeVidaDto, solicitudHojaDeVidaDto.getUsername());
    }

    @Override
    public Mono<Void> enviarInformacionEmpresa(PosicionEntrevistaDto perfil) {
        return enviarMensaje(topicEmpresa, perfil, perfil.getIdEntrevista());
    }

    @Override
    public Mono<Void> generarEntrevista(SolicitudGeneracionEntrevistaDto solicitudGeneracionEntrevista) {
        return enviarMensaje(topicGenerador, solicitudGeneracionEntrevista, solicitudGeneracionEntrevista.getIdEntrevista());
    }

    @Override
    public Mono<Void> validarmatchHojaDeVida(SolicitudMatchDto solicitudMatchDto) {
        return enviarMensaje(topicValidador, solicitudMatchDto, solicitudMatchDto.getIdEntrevista());
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
