package com.entrevistador.orquestador.infrastructure.adapter.jms;

import com.entrevistador.orquestador.application.usescases.OrquestadorEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.MensajeAnalizadorDto;
import com.entrevistador.orquestador.dominio.model.dto.MensajeAnalizadorEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.service.ActualizarEstadoProcesoEntrevistaService;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaAlternativaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JmsListenerAdapter {

    private final OrquestadorEntrevista orquestadorEntrevista;
    private final ActualizarEstadoProcesoEntrevistaService actualizarEstadoProcesoEntrevistaService;
    private final CrearEntrevistaAlternativaService crearEntrevistaAlternativaService;


    public JmsListenerAdapter(OrquestadorEntrevista orquestadorEntrevista, ActualizarEstadoProcesoEntrevistaService actualizarEstadoProcesoEntrevistaService, CrearEntrevistaAlternativaService crearEntrevistaAlternativaService) {
        this.orquestadorEntrevista = orquestadorEntrevista;
        this.actualizarEstadoProcesoEntrevistaService = actualizarEstadoProcesoEntrevistaService;
        this.crearEntrevistaAlternativaService = crearEntrevistaAlternativaService;
    }

    @KafkaListener(topics = "hojaDeVidaListenerTopic", groupId = "resumeGroup")
    public void receptorHojaDevida(String mensajeJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MensajeAnalizadorDto mensajeAnalizadorDto = objectMapper.readValue(mensajeJson, MensajeAnalizadorDto.class);
            this.actualizarEstadoProcesoEntrevistaService.ejecutar(mensajeAnalizadorDto.getProcesoEntrevista());
            this.orquestadorEntrevista.receptorHojaDeVida(mensajeAnalizadorDto.getIdEntrevista(), mensajeAnalizadorDto.getHojaDeVida());
        } catch (IOException e) {
            throw new RuntimeException("Error al deserializar el mensaje JSON", e);
        }
    }

    @KafkaListener(topics = "empresaListenerTopic", groupId = "resumeGroup")
    public void receptorInformacionEmpresa(String mensajeJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MensajeAnalizadorEmpresaDto mensajeAnalizadorDto = objectMapper.readValue(mensajeJson, MensajeAnalizadorEmpresaDto.class);
            this.actualizarEstadoProcesoEntrevistaService.ejecutar(mensajeAnalizadorDto.getProcesoEntrevista());
            this.orquestadorEntrevista.receptorInformacionEmpresa(mensajeAnalizadorDto.getIdEntrevista(), mensajeAnalizadorDto.getFormulario(), mensajeAnalizadorDto.getPreguntas());
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
        this.crearEntrevistaAlternativaService.ejecutar(procesoEntrevistaDto ,idEntrevista);
    }

}
