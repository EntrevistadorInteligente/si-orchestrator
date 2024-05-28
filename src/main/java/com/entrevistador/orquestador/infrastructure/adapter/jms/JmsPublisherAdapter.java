package com.entrevistador.orquestador.infrastructure.adapter.jms;

import com.entrevistador.orquestador.dominio.model.dto.*;
import com.entrevistador.orquestador.dominio.model.dto.SolicitudHojaDeVidaDto;
import com.entrevistador.orquestador.dominio.port.jms.JmsPublisherClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Slf4j
@Service
@RequiredArgsConstructor
public final class JmsPublisherAdapter implements JmsPublisherClient {


    private static final Logger logger = (Logger) LoggerFactory.getLogger(JmsPublisherAdapter.class);
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

        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(hojaDeVidaPublisherTopic, solicitudHojaDeVidaDto);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info(SENT_MESSAGE + solicitudHojaDeVidaDto.getUsername() +
                            WITH_OFFSET + result.getRecordMetadata().offset() + "]");
                } else {
                    logger.info(UNABLE_SEND_MESSAGE+
                            solicitudHojaDeVidaDto.toString() + DUE_TO + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            logger.info(ERROR + ex.getMessage());
        }

        return Mono.empty();
    }

    @Override
    public Mono<Void> enviarInformacionEmpresa(PosicionEntrevistaDto perfil) {

        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicEmpresa, perfil);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info(SENT_MESSAGE + perfil.getIdEntrevista() +
                            WITH_OFFSET + result.getRecordMetadata().offset() + "]");
                } else {
                    logger.info(UNABLE_SEND_MESSAGE+
                            perfil.toString() + DUE_TO + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            logger.info(ERROR + ex.getMessage());
        }

        return Mono.empty();
    }

    @Override
    public Mono<Void> generarEntrevista(SolicitudGeneracionEntrevistaDto solicitudGeneracionEntrevista) {
        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicGenerador, solicitudGeneracionEntrevista);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info(SENT_MESSAGE + solicitudGeneracionEntrevista.getIdEntrevista() +
                            WITH_OFFSET + result.getRecordMetadata().offset() + "]");
                } else {
                    logger.info(UNABLE_SEND_MESSAGE+
                            solicitudGeneracionEntrevista.toString() + DUE_TO + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            logger.info(ERROR + ex.getMessage());
        }

        return Mono.empty();
    }

    @Override
    public Mono<Void> validarmatchHojaDeVida(SolicitudMatchDto solicitudMatchDto) {
        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicValidador, solicitudMatchDto);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info(SENT_MESSAGE + solicitudMatchDto.getIdEntrevista() +
                            WITH_OFFSET + result.getRecordMetadata().offset() + "]");
                } else {
                    logger.info(UNABLE_SEND_MESSAGE+
                            solicitudMatchDto.toString() + DUE_TO + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            logger.info(ERROR + ex.getMessage());
        }

        return Mono.empty();
    }
}
