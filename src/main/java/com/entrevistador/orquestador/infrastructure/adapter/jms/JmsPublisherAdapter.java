package com.entrevistador.orquestador.infrastructure.adapter.jms;

import com.entrevistador.orquestador.dominio.model.dto.PosicionEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.PreparacionEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.client.AnalizadorClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public final class JmsPublisherAdapter  implements AnalizadorClient {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Value("${kafka.topic-analizador}")
    private String hojaDeVidaPublisherTopic;

    @Value("${kafka.topic-recopilador}")
    private String topicEmpresa;

    @Override
    public Mono<Void> enviarHojaDeVida(PreparacionEntrevistaDto preparacionEntrevistaDto) {

        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(hojaDeVidaPublisherTopic, preparacionEntrevistaDto);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + preparacionEntrevistaDto.getIdEntrevista() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                            preparacionEntrevistaDto.toString() + "] due to : " + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            System.out.println("ERROR : "+ ex.getMessage());
        }

        return Mono.empty();
    }

    @Override
    public Mono<Void> enviarInformacionEmpresa(PosicionEntrevistaDto perfil) {

        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicEmpresa, perfil);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + perfil.getIdEntrevista() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                            perfil.toString() + "] due to : " + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            System.out.println("ERROR : "+ ex.getMessage());
        }

        return Mono.empty();
    }

}
