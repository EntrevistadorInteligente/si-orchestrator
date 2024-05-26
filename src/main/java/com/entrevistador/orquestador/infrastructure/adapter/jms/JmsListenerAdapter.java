package com.entrevistador.orquestador.infrastructure.adapter.jms;

import com.entrevistador.orquestador.application.usescases.HojaDeVida;
import com.entrevistador.orquestador.application.usescases.OrquestadorEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.EntrevistaDto;
import com.entrevistador.orquestador.dominio.model.dto.FeedbackDto;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.MensajeAnalizadorEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.MensajeValidacionMatch;
import com.entrevistador.orquestador.dominio.service.ActualizarEstadoProcesoEntrevistaService;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaAlternativaService;
import com.entrevistador.orquestador.infrastructure.adapter.exceptions.ErrorDeserializarJson;
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

    @KafkaListener(topics = "hojaDeVidaListenerTopic", groupId = "resumeGroup2")
    public void receptorHojaDevida(String mensajeJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            HojaDeVidaDto mensajeAnalizador = objectMapper.readValue(mensajeJson, HojaDeVidaDto.class);
            Mono.just(mensajeAnalizador).flatMap(this.hojaDeVida::guardarHojaDeVida).block();

        } catch (IOException e) {
            throw new ErrorDeserializarJson(MENSAJE_ERROR);
        }
    }

    @KafkaListener(topics = "empresaListenerTopic", groupId = "resumeGroup2")
    public void receptorInformacionEmpresa(String mensajeJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            MensajeAnalizadorEmpresaDto mensajeAnalizador = objectMapper.readValue(mensajeJson, MensajeAnalizadorEmpresaDto.class);
            Mono.just(mensajeAnalizador)
                .flatMap(mensajeAnalizadorDto -> this.actualizarEstadoProcesoEntrevistaService.ejecutar(mensajeAnalizadorDto.getProcesoEntrevista()))
                .then(this.orquestadorEntrevista.receptorInformacionEmpresa(mensajeAnalizador.getIdEntrevista(), mensajeAnalizador.getIdInformacionEmpresaRag())).block();

        } catch (IOException e) {
            throw new ErrorDeserializarJson(MENSAJE_ERROR);
        }

    }

    @KafkaListener(topics = "hojaDeVidaValidaListenerTopic", groupId = "resumeGroup2")
    public void receptorValidarMatchHojaDeVida(String mensajeJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            MensajeValidacionMatch mensajeAnalizador = objectMapper.readValue(mensajeJson, MensajeValidacionMatch.class);
            Mono.just(mensajeAnalizador).flatMap(this.orquestadorEntrevista::receptorHojaDeVidaMatch).block();

        } catch (IOException e) {
            throw new ErrorDeserializarJson(MENSAJE_ERROR);
        }
    }

    @KafkaListener(topics = "feedbackListenerTopic", groupId = "resumeGroup2")
    public void receptorFeedBack(String jsonData) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            FeedbackDto json = mapper.readValue(jsonData, FeedbackDto.class);

            Mono.just(json)
                    .flatMap(this.orquestadorEntrevista::actualizarEstadoEntrevistaPorFeedback)
                    .block();
        } catch (JsonProcessingException e) {
            throw new ErrorDeserializarJson(MENSAJE_ERROR);
        }
    }

    @KafkaListener(topics = "preguntasListenerTopic", groupId = "my-group")
    public void receptorPreguntasEntrevista(String jsonData) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            EntrevistaDto json = mapper.readValue(jsonData, EntrevistaDto.class);
            Mono.just(json)
                    .flatMap(this.orquestadorEntrevista::actualizarEstadoEntrevistaPorPreguntas)
                    .block();
        } catch (JsonProcessingException e) {
            throw new ErrorDeserializarJson(MENSAJE_ERROR);
        }
    }

}
