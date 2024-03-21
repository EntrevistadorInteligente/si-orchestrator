package com.entrevistador.orquestador.infrastructure.adapter.jms;

import com.entrevistador.orquestador.application.usescases.OrquestadorEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.service.ActualizarEstadoProcesoEntrevistaService;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaAlternativaService;
import org.springframework.kafka.annotation.KafkaListener;

public class JmsListenerAdapter {

    private final OrquestadorEntrevista orquestadorEntrevista;
    private final ActualizarEstadoProcesoEntrevistaService actualizarEstadoProcesoEntrevistaService;
    private final CrearEntrevistaAlternativaService crearEntrevistaAlternativaService;


    public JmsListenerAdapter(OrquestadorEntrevista orquestadorEntrevista, ActualizarEstadoProcesoEntrevistaService actualizarEstadoProcesoEntrevistaService, CrearEntrevistaAlternativaService crearEntrevistaAlternativaService) {
        this.orquestadorEntrevista = orquestadorEntrevista;
        this.actualizarEstadoProcesoEntrevistaService = actualizarEstadoProcesoEntrevistaService;
        this.crearEntrevistaAlternativaService = crearEntrevistaAlternativaService;
    }

    @KafkaListener(topics = "resumeTopic", groupId = "resumeGroup")
    public void receptorHojaDevida(ProcesoEntrevistaDto procesoEntrevistaDto, String idEntrevista, HojaDeVidaDto resume) {
        this.actualizarEstadoProcesoEntrevistaService.ejecutar(procesoEntrevistaDto);
        this.orquestadorEntrevista.receptorHojaDeVida(idEntrevista, procesoEntrevistaDto.getUuid(), resume);
    }

    @KafkaListener(topics = "resumeTopic", groupId = "resumeGroup")
    public void receptorInformacionEmpresa(ProcesoEntrevistaDto procesoEntrevistaDto,String idEntrevista, InformacionEmpresaDto info) {
        this.actualizarEstadoProcesoEntrevistaService.ejecutar(procesoEntrevistaDto);
        this.orquestadorEntrevista.receptorInformacionEmpresa(idEntrevista, procesoEntrevistaDto.getUuid(), info);
    }

    @KafkaListener(topics = "resumeTopic", groupId = "resumeGroup")
    public void receptorActualizacionEstados(ProcesoEntrevistaDto procesoEntrevistaDto) {
        this.actualizarEstadoProcesoEntrevistaService.ejecutar(procesoEntrevistaDto);
    }

    @KafkaListener(topics = "resumeTopic", groupId = "resumeGroup")
    public void receptorErrorInvestigacionEmpresa(ProcesoEntrevistaDto procesoEntrevistaDto, String idEntrevista) {
        this.crearEntrevistaAlternativaService.ejecutar(procesoEntrevistaDto ,idEntrevista);
    }

}
