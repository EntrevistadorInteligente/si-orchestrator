package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;

public interface OrquestadorEntrevista {

    void receptorHojaDeVida(String idEntrevista,String eventoEntrevistaId, HojaDeVidaDto resume);
    void receptorInformacionEmpresa(String idEntrevista,String eventoEntrevistaId, InformacionEmpresaDto info);
    void generarEntrevistaConDatosDummy(String idEntrevista);
}
