package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.SolicitudEntrevista;
import com.entrevistador.orquestador.dominio.model.dto.AnalizarOfertaDto;
import com.entrevistador.orquestador.dominio.model.dto.PreparacionEntrevistaDto;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorPdfService;
import com.entrevistador.orquestador.infrastructure.adapter.client.AnalizadorClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SolicitudEntrevistaService implements SolicitudEntrevista {

    private final AnalizadorClient analizadorClient;
    private final ValidadorPdfService validadorPdfService;
    private final ProcesoEntrevistaDao procesoEntrevistaDao;
    private final CrearEntrevistaService crearEntrevistaService;

    public SolicitudEntrevistaService(AnalizadorClient analizadorClient, ValidadorPdfService validadorPdfService, ProcesoEntrevistaDao procesoEntrevistaDao, CrearEntrevistaService crearEntrevistaService) {
        this.analizadorClient = analizadorClient;
        this.validadorPdfService = validadorPdfService;
        this.procesoEntrevistaDao = procesoEntrevistaDao;
        this.crearEntrevistaService = crearEntrevistaService;
    }

    @Override
    public void generarSolicitudEntrevista(MultipartFile file) {
        byte[] sdf = this.validadorPdfService.ejecutar(file);
        var idEntrevista = this.crearEntrevistaService.ejecutar();
        var eventoEntrevista = procesoEntrevistaDao.crearEvento();
        this.analizadorClient.enviarHojaDeVida(PreparacionEntrevistaDto.builder()
                .idEntrevista(idEntrevista)
                .eventoEntrevistaId(eventoEntrevista.getId())
                .hojadevida(sdf)
                .build());
    }

    @Override
    public void generarOfertaLaboral(MultipartFile file) {
        byte[] ofertaLaboralBytes = this.validadorPdfService.ejecutar(file);
        this.analizadorClient.enviarOfertaLaboral(AnalizarOfertaDto
                .builder()
                .ofertaLaboral(ofertaLaboralBytes)
                .build());
    }

}

