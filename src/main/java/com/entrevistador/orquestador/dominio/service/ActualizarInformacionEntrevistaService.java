package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.model.Entrevista;
import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class ActualizarInformacionEntrevistaService {
    private final EntrevistaDao entrevistaDao;

    public Mono<String> actualizarHojaDeVida(String idEntrevista, HojaDeVidaDto resume) {
        Entrevista entrevista = Entrevista.builder()
                .uuid(idEntrevista)
                .hojaDeVidaDto(resume)
                .build();
        return entrevistaDao.actualizarEntrevista(entrevista);
    }

    public Mono<String> actualizarInformacionEmpresa(String idEntrevista, FormularioDto info, List<String> preguntas) {
        Entrevista entrevista = Entrevista.builder()
                .uuid(idEntrevista)
                .informacionEmpresaDto(InformacionEmpresaDto.builder()
                        .empresa(info.getEmpresa())
                        .pais(info.getPais())
                        .perfil(info.getPerfil())
                        .seniority(info.getSeniority())
                        .preguntas(preguntas)
                        .build())
                .build();
        return entrevistaDao.actualizarEntrevista(entrevista);
    }
}
