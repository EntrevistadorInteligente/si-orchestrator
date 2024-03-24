package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.model.Entrevista;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ActualizarInformacionEntrevistaService {
    private final EntrevistaDao entrevistaDao;
    public String actualizarHojaDeVida(String idEntrevista, HojaDeVidaDto resume){
        Entrevista entrevista = Entrevista.builder()
                .uuid(idEntrevista)
                .idEvento("")
                .hojaDeVidaDto(resume)
                .informacionEmpresaDto(null)
                .build();
        return entrevistaDao.actualizarEntrevista(entrevista).block();
    }

    public String actualizarInrfomacionEmpresa(String idEntrevista, InformacionEmpresaDto info){
        Entrevista entrevista = Entrevista.builder()
                .uuid(idEntrevista)
                .idEvento("")
                .hojaDeVidaDto(null)
                .informacionEmpresaDto(info)
                .build();
        return entrevistaDao.actualizarEntrevista(entrevista).block();
    }

}
