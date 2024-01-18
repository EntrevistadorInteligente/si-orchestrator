package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.PreparacionEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorPdfService;
import com.entrevistador.orquestador.infrastructure.adapter.client.AnalizadorClient;
import com.entrevistador.orquestador.infrastructure.adapter.client.RecopiladorEmpresaClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SolicitudEntrevistaService implements SolicitudEntrevista {

    private final AnalizadorClient analizadorClient;
    private final ValidadorPdfService validadorPdfService;
    private final ProcesoEntrevistaDao procesoEntrevistaDao;
    private final CrearEntrevistaService crearEntrevistaService;
    private final RecopiladorEmpresaClient recopiladorEmpresaClient;

    public SolicitudEntrevistaService(AnalizadorClient analizadorClient, ValidadorPdfService validadorPdfService, ProcesoEntrevistaDao procesoEntrevistaDao, CrearEntrevistaService crearEntrevistaService, RecopiladorEmpresaClient recopiladorEmpresaClient) {
        this.analizadorClient = analizadorClient;
        this.validadorPdfService = validadorPdfService;
        this.procesoEntrevistaDao = procesoEntrevistaDao;
        this.crearEntrevistaService = crearEntrevistaService;
        this.recopiladorEmpresaClient = recopiladorEmpresaClient;
    }

    @Override
    @Async
    public void generarSolicitudEntrevista(byte[] hojaDeVida, FormularioDto formulario) {
        this.validadorPdfService.ejecutar(hojaDeVida);
        var idEntrevista = this.crearEntrevistaService.ejecutar();
        var eventoEntrevista = procesoEntrevistaDao.crearEvento();

        //FUTURE Simultaneamente

        this.analizadorClient.enviarHojaDeVida(PreparacionEntrevistaDto.builder()
                        .idEntrevista(idEntrevista)
                        .eventoEntrevistaId(eventoEntrevista.getId())
                        .hojadevida(hojaDeVida)
                .build());

        // formulario
        this.recopiladorEmpresaClient.enviarInformacionEmpresa(formulario);

    }

}

