package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.application.dto.RagsIdsDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ValidadorEventosSimultaneosService {

    private final EntrevistaDao entrevistaDao;

    public Mono<RagsIdsDto> ejecutar(String idEntrevista) {
        return entrevistaDao.consultarRagsId(idEntrevista)
                .filter(this::hasIds)
                .flatMap(Mono::just);
    }

    private boolean hasIds(RagsIdsDto ragsIdsDto) {
        String idHojaDeVida = ragsIdsDto.getIdHojaDeVidaRag();
        String idInformacionEmpresa = ragsIdsDto.getIdInformacionEmpresaRag();
        return idHojaDeVida != null && idInformacionEmpresa != null && ragsIdsDto.getHojaDeVidaValida();
    }

}
