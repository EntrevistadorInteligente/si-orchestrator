package com.entrevistador.orquestador.infrastructure.adapter.jms;

import com.entrevistador.orquestador.application.usescases.OrquestadorEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
import com.entrevistador.orquestador.dominio.service.ActualizarEstadoProvesoEntrevistaService;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaAlternativaService;
import org.springframework.kafka.annotation.KafkaListener;

public class JmsListenerAdapter {

    private final OrquestadorEntrevista orquestadorEntrevista;
    private final ActualizarEstadoProvesoEntrevistaService actualizarEstadoProvesoEntrevistaService;
    private final CrearEntrevistaAlternativaService crearEntrevistaAlternativaService;


    public JmsListenerAdapter(OrquestadorEntrevista orquestadorEntrevista, ActualizarEstadoProvesoEntrevistaService actualizarEstadoProvesoEntrevistaService, CrearEntrevistaAlternativaService crearEntrevistaAlternativaService) {
        this.orquestadorEntrevista = orquestadorEntrevista;
        this.actualizarEstadoProvesoEntrevistaService = actualizarEstadoProvesoEntrevistaService;
        this.crearEntrevistaAlternativaService = crearEntrevistaAlternativaService;
    }

    @KafkaListener(topics = "resumeTopic", groupId = "resumeGroup")
    public void receptorHojaDevida(String idEvento, EstadoProcesoEnum estadoProcesoEnum, String idEntrevista, HojaDeVidaDto resume) {
        this.actualizarEstadoProvesoEntrevistaService.ejecutar(idEvento, estadoProcesoEnum);
        this.orquestadorEntrevista.receptorHojaDeVida(idEntrevista, idEvento, resume);
    }

    @KafkaListener(topics = "resumeTopic", groupId = "resumeGroup")
    public void receptorInformacionEmpresa(String idEvento, EstadoProcesoEnum estadoProcesoEnum,String idEntrevista, InformacionEmpresaDto info) {
        this.actualizarEstadoProvesoEntrevistaService.ejecutar(idEvento, estadoProcesoEnum);
        this.orquestadorEntrevista.receptorInformacionEmpresa(idEntrevista, idEvento, info);
    }

    @KafkaListener(topics = "resumeTopic", groupId = "resumeGroup")
    public void receptorActualizacionEstados(String idEvento, EstadoProcesoEnum estadoProcesoEnum) {
        this.actualizarEstadoProvesoEntrevistaService.ejecutar(idEvento,estadoProcesoEnum);
    }

    @KafkaListener(topics = "resumeTopic", groupId = "resumeGroup")
    public void receptorErrorInvestigacionEmpresa(String idEvento, EstadoProcesoEnum estadoProcesoEnum, String idEntrevista) {
        this.crearEntrevistaAlternativaService.ejecutar(idEvento,estadoProcesoEnum,idEntrevista);
    }

}
