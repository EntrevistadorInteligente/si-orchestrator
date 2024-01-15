package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;

public class CrearEntrevistaAlternativaService {
    private final NotificarFrontEntrevistaFallidaService notificarFrontEntrevistaFallidaService;

    public CrearEntrevistaAlternativaService(NotificarFrontEntrevistaFallidaService notificarFrontEntrevistaFallidaService) {
        this.notificarFrontEntrevistaFallidaService = notificarFrontEntrevistaFallidaService;
    }

    public String ejecutar(String idEvento, EstadoProcesoEnum estadoProcesoEnum, String idEntrevista){
        return null;
    }

}
