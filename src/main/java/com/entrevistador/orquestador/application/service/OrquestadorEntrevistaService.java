package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.OrquestadorEntrevista;
import com.entrevistador.orquestador.dominio.model.Entrevista;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.service.ActualizarInformacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.SolicitudPreparacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorEventosSimultaneosService;
import com.entrevistador.orquestador.dominio.port.client.PreparadorClient;
import com.entrevistador.orquestador.infrastructure.adapter.entity.EntrevistaEntity;
import org.springframework.stereotype.Service;


@Service
public class OrquestadorEntrevistaService implements OrquestadorEntrevista {

    private final SolicitudPreparacionEntrevistaService solicitudPreparacionEntrevistaService;
    private final ActualizarInformacionEntrevistaService actualizarInformacionEntrevistaService;
    private final ValidadorEventosSimultaneosService validadorEventosSimultaneosService;
    private final PreparadorClient preparadorClient;

    public OrquestadorEntrevistaService(SolicitudPreparacionEntrevistaService solicitudPreparacionEntrevistaService, ActualizarInformacionEntrevistaService actualizarInformacionEntrevistaService, ValidadorEventosSimultaneosService validadorEventosSimultaneosService, PreparadorClient preparadorClient) {
        this.solicitudPreparacionEntrevistaService = solicitudPreparacionEntrevistaService;
        this.actualizarInformacionEntrevistaService = actualizarInformacionEntrevistaService;
        this.validadorEventosSimultaneosService = validadorEventosSimultaneosService;
        this.preparadorClient = preparadorClient;
    }

    @Override
    public void receptorHojaDeVida(String idEntrevista, HojaDeVidaDto resume) {
        String campoInformacionEmpresa;
        campoInformacionEmpresa = this.actualizarInformacionEntrevistaService.actualizarHojaDeVida(idEntrevista, resume);
        var eventosFinalizados = this.validadorEventosSimultaneosService.ejecutar(idEntrevista, campoInformacionEmpresa, "hojadevida");
        enviarInformacionEntrevistaAPreparador(eventosFinalizados);
    }

    @Override
    public void receptorInformacionEmpresa(String idEntrevista, InformacionEmpresaDto info) {
        String campoHojaDeVida;
        campoHojaDeVida = this.actualizarInformacionEntrevistaService.actualizarInrfomacionEmpresa(idEntrevista, info);
        var eventosFinalizados = this.validadorEventosSimultaneosService.ejecutar(idEntrevista, campoHojaDeVida, "infoempresa");
        enviarInformacionEntrevistaAPreparador(eventosFinalizados);
    }

    @Override
    public void generarEntrevistaConDatosDummy(String idEntrevista){
        enviarInformacionEntrevistaAPreparador(true);
    }

    private void enviarInformacionEntrevistaAPreparador(boolean eventosFinalizados){
        if(eventosFinalizados){
            this.solicitudPreparacionEntrevistaService.ejecutar();
            this.preparadorClient.generarEntrevista();
        }

    }

}

