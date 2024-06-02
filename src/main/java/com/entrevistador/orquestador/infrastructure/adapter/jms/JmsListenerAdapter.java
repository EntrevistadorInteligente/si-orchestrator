package com.entrevistador.orquestador.infrastructure.adapter.jms;

import com.entrevistador.orquestador.infrastructure.adapter.dto.EntrevistaDto;
import com.entrevistador.orquestador.infrastructure.adapter.dto.FeedbackDto;
import com.entrevistador.orquestador.infrastructure.adapter.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.infrastructure.adapter.dto.MensajeAnalizadorEmpresaDto;
import com.entrevistador.orquestador.infrastructure.adapter.dto.MensajeValidacionMatchDto;
import com.entrevistador.orquestador.application.usescases.HojaDeVida;
import com.entrevistador.orquestador.application.usescases.OrquestadorEntrevista;
import com.entrevistador.orquestador.dominio.service.ActualizarEstadoProcesoEntrevistaService;
import com.entrevistador.orquestador.infrastructure.adapter.exceptions.ErrorDeserializarJson;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.HojaDeVidaMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JmsListenerAdapter {

    private static final String MENSAJE_ERROR = "Error al deserializar el mensaje JSON";
    private final OrquestadorEntrevista orquestadorEntrevista;
    private final ActualizarEstadoProcesoEntrevistaService actualizarEstadoProcesoEntrevistaService;
    private final HojaDeVida hojaDeVida;
    private final HojaDeVidaMapper mapper;

    @KafkaListener(topics = "hojaDeVidaListenerTopic", groupId = "resumeGroup2")
    public void receptorHojaDevida(String mensajeJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            HojaDeVidaDto mensajeAnalizador = objectMapper.readValue(mensajeJson, HojaDeVidaDto.class);
            Mono.just(this.mapper.mapHojaDeVidaDtoToHojaDeVida(mensajeAnalizador))
                    .flatMap(this.hojaDeVida::guardarHojaDeVida)
                    .block();

        } catch (IOException e) {
            throw new ErrorDeserializarJson(MENSAJE_ERROR, e);
        }
    }

    @KafkaListener(topics = "empresaListenerTopic", groupId = "resumeGroup2")
    public void receptorInformacionEmpresa(String mensajeJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            MensajeAnalizadorEmpresaDto mensajeAnalizador = objectMapper.readValue(mensajeJson, MensajeAnalizadorEmpresaDto.class);
            Mono.just(this.mapper.mapMensajeAnalizadorEmpresaDtoToMensajeAnalizadorEmpresa(mensajeAnalizador))
                    .flatMap(mensajeAnalizadorEmpresa -> this.actualizarEstadoProcesoEntrevistaService
                            .ejecutar(mensajeAnalizadorEmpresa.getProcesoEntrevista()))
                    .then(this.orquestadorEntrevista.receptorInformacionEmpresa(mensajeAnalizador.getIdEntrevista(),
                            mensajeAnalizador.getIdInformacionEmpresaRag()))
                    .block();

        } catch (IOException e) {
            throw new ErrorDeserializarJson(MENSAJE_ERROR, e);
        }

    }

    @KafkaListener(topics = "hojaDeVidaValidaListenerTopic", groupId = "resumeGroup2")
    public void receptorValidarMatchHojaDeVida(String mensajeJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            MensajeValidacionMatchDto mensajeAnalizador = objectMapper.readValue(mensajeJson, MensajeValidacionMatchDto.class);
            Mono.just(this.mapper.mapMensajeValidacionMatchDtoToMensajeValidacionMatch(mensajeAnalizador))
                    .flatMap(this.orquestadorEntrevista::receptorHojaDeVidaMatch)
                    .block();

        } catch (IOException e) {
            throw new ErrorDeserializarJson(MENSAJE_ERROR, e);
        }
    }

    @KafkaListener(topics = "feedbackListenerTopic", groupId = "resumeGroup2")
    public void receptorFeedBack(String jsonData) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            FeedbackDto feedbackDto = objectMapper.readValue(jsonData, FeedbackDto.class);

            Mono.just(this.mapper.mapFeedbackDtoToFeedback(feedbackDto))
                    .flatMap(this.orquestadorEntrevista::actualizarEstadoEntrevistaPorFeedback)
                    .block();
        } catch (JsonProcessingException e) {
            throw new ErrorDeserializarJson(MENSAJE_ERROR, e);
        }
    }

    @KafkaListener(topics = "preguntasListenerTopic", groupId = "my-group")
    public void receptorPreguntasEntrevista(String jsonData) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            EntrevistaDto entrevistaDto = objectMapper.readValue(jsonData, EntrevistaDto.class);
            Mono.just(this.mapper.mapEntrevistaDtoToEntrevistaModel(entrevistaDto))
                    .flatMap(this.orquestadorEntrevista::actualizarEstadoEntrevistaPorPreguntas)
                    .block();
        } catch (JsonProcessingException e) {
            throw new ErrorDeserializarJson(MENSAJE_ERROR, e);
        }
    }

}
