package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ValidadorEventosSimultaneosService {

    private final EntrevistaDao entrevistaDao;

    public Mono<RagsIdsDto> ejecutar(String idEntrevista) {
      return entrevistaDao.consultarRagsId(idEntrevista)
          .flatMap(ragsIdsDto -> {
            String idHojaDeVida = ragsIdsDto.getIdHojaDeVidaRag();
            String idInformacionEmpresa = ragsIdsDto.getIdInformacionEmpresaRag();
            if (idHojaDeVida != null && idInformacionEmpresa != null){
                return Mono.just(ragsIdsDto);
            } else{
                return Mono.empty();
            }
          });
    }

}
