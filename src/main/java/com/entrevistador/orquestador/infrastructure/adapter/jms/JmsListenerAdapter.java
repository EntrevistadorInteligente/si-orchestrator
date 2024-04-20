package com.entrevistador.orquestador.infrastructure.adapter.jms;

import com.entrevistador.orquestador.application.usescases.HojaDeVida;
import com.entrevistador.orquestador.application.usescases.OrquestadorEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.MensajeAnalizadorEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.MensajeValidacionMatch;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.service.ActualizarEstadoProcesoEntrevistaService;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaAlternativaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JmsListenerAdapter {
    private final OrquestadorEntrevista orquestadorEntrevista;
    private final ActualizarEstadoProcesoEntrevistaService actualizarEstadoProcesoEntrevistaService;
    private final CrearEntrevistaAlternativaService crearEntrevistaAlternativaService;
    private final HojaDeVida hojaDeVida;

    @KafkaListener(topics = "hojaDeVidaListenerTopic", groupId = "resumeGroup")
    public void receptorHojaDevida(String mensajeJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            HojaDeVidaDto mensajeAnalizador = objectMapper.readValue(mensajeJson, HojaDeVidaDto.class);
            Mono.just(mensajeAnalizador).flatMap(this.hojaDeVida::guardarHojaDeVida).block();

        } catch (IOException e) {
            throw new RuntimeException("Error al deserializar el mensaje JSON", e);
        }
    }

    @KafkaListener(topics = "empresaListenerTopic", groupId = "resumeGroup")
    public void receptorInformacionEmpresa(String mensajeJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            MensajeAnalizadorEmpresaDto mensajeAnalizador = objectMapper.readValue(mensajeJson, MensajeAnalizadorEmpresaDto.class);
            Mono.just(mensajeAnalizador)
                .flatMap(mensajeAnalizadorDto -> this.actualizarEstadoProcesoEntrevistaService.ejecutar(mensajeAnalizadorDto.getProcesoEntrevista()))
                .then(this.orquestadorEntrevista.receptorInformacionEmpresa(mensajeAnalizador.getIdEntrevista(), mensajeAnalizador.getIdInformacionEmpresaRag())).block();

        } catch (IOException e) {
            throw new RuntimeException("Error al deserializar el mensaje JSON", e);
        }

    }

    @KafkaListener(topics = "hojaDeVidaValidaListenerTopic", groupId = "resumeGroup")
    public void receptorValidarMatchHojaDeVida(String mensajeJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            MensajeValidacionMatch mensajeAnalizador = objectMapper.readValue(mensajeJson, MensajeValidacionMatch.class);
            Mono.just(mensajeAnalizador).flatMap(this.orquestadorEntrevista::receptorHojaDeVidaMatch).block();

        } catch (IOException e) {
            throw new RuntimeException("Error al deserializar el mensaje JSON", e);
        }
    }

    @KafkaListener(topics = "resumeTopic2", groupId = "actualizacionEstados")
    public void receptorActualizacionEstados(ProcesoEntrevistaDto procesoEntrevistaDto) {
        this.actualizarEstadoProcesoEntrevistaService.ejecutar(procesoEntrevistaDto);
    }

    @KafkaListener(topics = "resumeTopic2", groupId = "investigacionEmpresa")
    public void receptorErrorInvestigacionEmpresa(ProcesoEntrevistaDto procesoEntrevistaDto, String idEntrevista) {
        this.crearEntrevistaAlternativaService.ejecutar(procesoEntrevistaDto, idEntrevista);
    }

    @KafkaListener(topics = "resumeTopic2", groupId = "investigacionEmpresa")
    public void receptorErrorInvestigacionEmpresa(List<String> preguntas) {
        Mono.just(this.orquestadorEntrevista.enviarPreguntasFront(preguntas).block());
    }

}
