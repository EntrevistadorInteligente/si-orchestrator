package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.model.Entrevista;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ActualizarInformacionEntrevistaService {

    private final EntrevistaDao entrevistaDao;

    public Mono<Void> actualizarEstadoEntrevistaSegunMatch(String idEntrevista, boolean esEntrevistaValida) {
        return entrevistaDao.actualizarEstadoEntrevista(idEntrevista, esEntrevistaValida);
    }

    public Mono<Void> actualizarInformacionEmpresa(String idEntrevista, InformacionEmpresaDto info) {
        Entrevista entrevista = Entrevista.builder()
                .uuid(idEntrevista)
                .informacionEmpresaDto(info)
                .build();
        return entrevistaDao.actualizarEntrevista(entrevista);
    }
}
