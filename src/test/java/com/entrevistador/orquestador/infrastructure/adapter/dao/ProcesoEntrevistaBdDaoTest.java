package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.model.ProcesoEntrevista;
import com.entrevistador.orquestador.infrastructure.adapter.entity.ProcesoEntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.ProcesoEntrevistaMapper;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcesoEntrevistaBdDaoTest {
    @InjectMocks
    private ProcesoEntrevistaBdDao procesoEntrevistaBdDao;
    @Mock
    private ProcesoEntrevistaRepository procesoEntrevistaRepository;
    @Mock
    private ProcesoEntrevistaMapper mapper;

    @Test
    void testCrearEvento() {
        ProcesoEntrevista procesoEntrevista = ProcesoEntrevista.builder().build();

        when(this.procesoEntrevistaRepository.save(any()))
                .thenReturn(Mono.just(ProcesoEntrevistaEntity.builder().build()));
        when(this.mapper.mapProcesoEntrevistaEntityToProcesoEntrevista(any()))
                .thenReturn(procesoEntrevista);

        Mono<ProcesoEntrevista> publisher = this.procesoEntrevistaBdDao.crearEvento();

        StepVerifier
                .create(publisher)
                .expectNext(procesoEntrevista)
                .verifyComplete();

        verify(this.procesoEntrevistaRepository, times(1)).save(any());
        verify(this.mapper, times(1)).mapProcesoEntrevistaEntityToProcesoEntrevista(any());
    }

    @Test
    void testObtenerEventoPorId() {
        ProcesoEntrevista procesoEntrevista = ProcesoEntrevista.builder().build();

        when(this.procesoEntrevistaRepository.findById(anyString()))
                .thenReturn(Mono.just(ProcesoEntrevistaEntity.builder().build()));
        when(this.mapper.mapProcesoEntrevistaEntityToProcesoEntrevista(any()))
                .thenReturn(procesoEntrevista);

        Mono<ProcesoEntrevista> publisher = this.procesoEntrevistaBdDao.obtenerEventoPorId("id");

        StepVerifier
                .create(publisher)
                .expectNext(procesoEntrevista)
                .verifyComplete();

        verify(this.procesoEntrevistaRepository, times(1)).findById(anyString());
        verify(this.mapper, times(1)).mapProcesoEntrevistaEntityToProcesoEntrevista(any());
    }

    @Test
    void testActualizar() {
        ProcesoEntrevista procesoEntrevista = ProcesoEntrevista.builder().build();

        when(this.mapper.mapProcesoEntrevistaToProcesoEntrevistaEntity(any()))
                .thenReturn(ProcesoEntrevistaEntity.builder().build());
        when(this.procesoEntrevistaRepository.save(any()))
                .thenReturn(Mono.just(ProcesoEntrevistaEntity.builder().build()));
        when(this.mapper.mapProcesoEntrevistaEntityToProcesoEntrevista(any()))
                .thenReturn(procesoEntrevista);

        Mono<ProcesoEntrevista> publisher = this.procesoEntrevistaBdDao.actualizar(procesoEntrevista);

        StepVerifier
                .create(publisher)
                .expectNext(procesoEntrevista)
                .verifyComplete();

        verify(this.mapper, times(1)).mapProcesoEntrevistaToProcesoEntrevistaEntity(any());
        verify(this.procesoEntrevistaRepository, times(1)).save(any());
        verify(this.mapper, times(1)).mapProcesoEntrevistaEntityToProcesoEntrevista(any());
    }
}