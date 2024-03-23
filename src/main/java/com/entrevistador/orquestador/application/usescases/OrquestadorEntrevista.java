package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;

import java.util.List;

public interface OrquestadorEntrevista {

    void receptorHojaDeVida(String idEntrevista, HojaDeVidaDto resume);
    void receptorInformacionEmpresa(String idEntrevista, FormularioDto info, List<String> preguntas);
    void generarEntrevistaConDatosDummy(String idEntrevista);
}
