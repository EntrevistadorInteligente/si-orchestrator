package com.entrevistador.orquestador.infrastructure.adapter.jms;

import com.entrevistador.orquestador.application.usescases.OrquestadorEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
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
    public void receptorHojaDevida(String idEvento, EstadoProcesoEnum estadoProcesoEnum, String idEntrevista, HojaDeVidaDto resume) {
        //this.actualizarEstadoProcesoEntrevistaService.ejecutar(idEvento, estadoProcesoEnum);
        this.orquestadorEntrevista.receptorHojaDeVida(idEntrevista, idEvento, resume);
    }

    @KafkaListener(topics = "resumeTopic", groupId = "resumeGroup")
    public void receptorInformacionEmpresa(String idEvento, EstadoProcesoEnum estadoProcesoEnum,String idEntrevista, InformacionEmpresaDto info) {
        //this.actualizarEstadoProcesoEntrevistaService.ejecutar(idEvento, estadoProcesoEnum);
        this.orquestadorEntrevista.receptorInformacionEmpresa(idEntrevista, idEvento, info);
    }

    @KafkaListener(topics = "resumeTopic", groupId = "resumeGroup")
    public void receptorActualizacionEstados(ProcesoEntrevistaDto procesoEntrevistaDto) { // (String idEvento, EstadoProcesoEnum estadoProcesoEnum)
        this.actualizarEstadoProcesoEntrevistaService.ejecutar(procesoEntrevistaDto); // (idEvento,estadoProcesoEnum)
    }

    @KafkaListener(topics = "resumeTopic", groupId = "resumeGroup")
    public void receptorErrorInvestigacionEmpresa(String idEvento, EstadoProcesoEnum estadoProcesoEnum, String idEntrevista) {
        this.crearEntrevistaAlternativaService.ejecutar(idEvento,estadoProcesoEnum,idEntrevista);
    }

}
