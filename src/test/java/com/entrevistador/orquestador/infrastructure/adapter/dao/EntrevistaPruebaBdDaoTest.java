package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.model.IdEntrevista;
import com.entrevistador.orquestador.dominio.model.SoloPerfil;
import com.entrevistador.orquestador.infrastructure.adapter.entity.EntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.EntrevistaPruebaMapper;
import com.entrevistador.orquestador.infrastructure.adapter.repository.EntrevistaRepository;
import com.entrevistador.orquestador.infrastructure.properties.EntrevistaPruebaConst;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntrevistaPruebaBdDaoTest {
    @InjectMocks
    private EntrevistaPruebaBdDao entrevistaPruebaBdDao;
    @Mock
    private EntrevistaRepository entrevistaRepository;
    @Mock
    private EntrevistaPruebaConst entrevistaPruebaConst;
    @Mock
    private EntrevistaPruebaMapper mapper;

    @Test
    void testGetPerfil() {
        SoloPerfil soloPerfil = SoloPerfil.builder().perfilEmpresa("any").build();

        when(this.entrevistaPruebaConst.getLimitperfiles()).thenReturn(1);
        when(this.entrevistaRepository.obtenerPerfilEmpresa(Mockito.anyInt()))
                .thenReturn(Flux.just(EntrevistaEntity.builder().build()));
        when(this.mapper.mapEntrevistaEntityToSoloPerfil(Mockito.any()))
                .thenReturn(soloPerfil);

        Flux<SoloPerfil> publisher = this.entrevistaPruebaBdDao.getPerfil();

        StepVerifier
                .create(publisher)
                .expectNext(soloPerfil)
                .verifyComplete();

        verify(this.entrevistaRepository, times(1)).obtenerPerfilEmpresa(Mockito.anyInt());
        verify(this.mapper, times(1)).mapEntrevistaEntityToSoloPerfil(any());
    }

    @Test
    void testGetIdEntrevistaByPerfil() {
        IdEntrevista idEntrevista = IdEntrevista.builder().id("any").build();

        when(this.entrevistaRepository.obtenerIdEntrevistaPorPerfil(Mockito.anyString()))
                .thenReturn(Mono.just(EntrevistaEntity.builder().build()));
        when(this.mapper.mapEntrevistaEntityToIdEntrevista(Mockito.any()))
                .thenReturn(idEntrevista);

        Mono<IdEntrevista> publisher = this.entrevistaPruebaBdDao.getIdEntrevistaByPerfil("any");

        StepVerifier
                .create(publisher)
                .expectNext(idEntrevista)
                .verifyComplete();

        verify(this.entrevistaRepository, times(1)).obtenerIdEntrevistaPorPerfil(Mockito.anyString());
        verify(this.mapper, times(1)).mapEntrevistaEntityToIdEntrevista(any());
    }
}