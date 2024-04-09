package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import reactor.core.publisher.Mono;

import java.util.Objects;


public class ValidadorEventosSimultaneosService {
    EntrevistaDao entrevistaDao;

    public Mono<RagsIdsDto> ejecutar(String idEntrevista) {
      return entrevistaDao.consultarRagsId(idEntrevista)
          .flatMap(ragsIdsDto -> {
            String idHojaDeVida = ragsIdsDto.getIdHojaDeVidaRag();
            String idInformacionEmpresa = ragsIdsDto.getIdInformacionEmpresaRag();
            if (Objects.nonNull(idHojaDeVida) && Objects.nonNull(idInformacionEmpresa))
              return Mono.just(ragsIdsDto);
            else
              return Mono.empty();
          });
    }

}
