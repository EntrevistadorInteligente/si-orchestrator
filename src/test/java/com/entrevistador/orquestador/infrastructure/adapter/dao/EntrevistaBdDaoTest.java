package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.excepciones.IdNoEncontradoException;
import com.entrevistador.orquestador.dominio.model.Entrevista;
import com.entrevistador.orquestador.dominio.model.EstadoEntrevista;
import com.entrevistador.orquestador.dominio.model.Formulario;
import com.entrevistador.orquestador.dominio.model.enums.EstadoEntrevistaEnum;
import com.entrevistador.orquestador.infrastructure.adapter.dto.RagsIdsDto;
import com.entrevistador.orquestador.infrastructure.adapter.entity.EntrevistaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.EntrevistaMapper;
import com.entrevistador.orquestador.infrastructure.adapter.repository.EntrevistaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntrevistaBdDaoTest {
    @InjectMocks
    private EntrevistaBdDao entrevistaBdDao;
    @Mock
    private EntrevistaRepository entrevistaRepository;
    @Mock
    private EntrevistaMapper mapper;

    @Test
    void testCrearEntrevistaBase() {
        EntrevistaEntity entrevistaEntity = EntrevistaEntity.builder().uuid("any").build();

        when(this.mapper.mapFormularioToEntrevistaEntity(anyString(), anyString(), any())).thenReturn(EntrevistaEntity.builder().build());
        when(this.entrevistaRepository.save(any())).thenReturn(Mono.just(entrevistaEntity));

        Mono<String> publisher = this.entrevistaBdDao.crearEntrevistaBase("any", "any", Formulario.builder().build());

        StepVerifier
                .create(publisher)
                .expectNext(entrevistaEntity.getUuid())
                .verifyComplete();

        verify(this.mapper, times(1)).mapFormularioToEntrevistaEntity(anyString(), anyString(), any());
        verify(this.entrevistaRepository, times(1)).save(any());
    }

    @Test
    void testActualizarEntrevista() {
        EntrevistaEntity entrevistaEntity = EntrevistaEntity.builder().uuid("any").build();

        when(this.entrevistaRepository.findById(anyString())).thenReturn(Mono.just(entrevistaEntity));
        when(this.mapper.mapEntrevistaToEntrevistaEntity(any(), any())).thenReturn(EntrevistaEntity.builder().build());
        when(this.entrevistaRepository.save(any())).thenReturn(Mono.just(entrevistaEntity));

        Mono<Void> publisher = this.entrevistaBdDao.actualizarEntrevista(Entrevista.builder().uuid("any").build());

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaRepository, times(1)).findById(anyString());
        verify(this.mapper, times(1)).mapEntrevistaToEntrevistaEntity(any(), any());
        verify(this.entrevistaRepository, times(1)).save(any());
    }

    @Test
    void testActualizarEntrevista_IdNoEncontradoExcepction() {
        when(this.entrevistaRepository.findById(anyString())).thenReturn(Mono.empty());

        Mono<Void> publisher = this.entrevistaBdDao.actualizarEntrevista(Entrevista.builder().uuid("any").build());

        StepVerifier
                .create(publisher)
                .expectError(IdNoEncontradoException.class)
                .verify();

        verify(this.entrevistaRepository, times(1)).findById(anyString());
        verify(this.mapper, times(0)).mapEntrevistaToEntrevistaEntity(any(), any());
        verify(this.entrevistaRepository, times(0)).save(any());
    }

    @Test
    void testConsultarRagsId() {
        when(this.entrevistaRepository.obtenerRagsYEstadoEntrevistaPorId(anyString())).thenReturn(Mono.empty());

        Mono<RagsIdsDto> publisher = this.entrevistaBdDao.consultarRagsId("any");

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaRepository, times(1)).obtenerRagsYEstadoEntrevistaPorId(anyString());
    }

    @Test
    void testActualizarEstadoHojaDeVida() {
        EntrevistaEntity entrevistaEntity = EntrevistaEntity.builder().uuid("any").build();

        when(this.entrevistaRepository.findById(anyString())).thenReturn(Mono.just(entrevistaEntity));
        when(this.entrevistaRepository.save(any())).thenReturn(Mono.just(entrevistaEntity));

        Mono<Void> publisher = this.entrevistaBdDao.actualizarEstadoHojaDeVida("any", true);

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaRepository, times(1)).findById(anyString());
        verify(this.entrevistaRepository, times(1)).save(any());
    }

    @Test
    void testActualizarIdInformacionEmpresaRag() {
        EntrevistaEntity entrevistaEntity = EntrevistaEntity.builder().uuid("any").build();

        when(this.entrevistaRepository.findById(anyString())).thenReturn(Mono.just(entrevistaEntity));
        when(this.entrevistaRepository.save(any())).thenReturn(Mono.just(entrevistaEntity));

        Mono<Void> publisher = this.entrevistaBdDao.actualizarIdInformacionEmpresaRag("any", "any");

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaRepository, times(1)).findById(anyString());
        verify(this.entrevistaRepository, times(1)).save(any());
    }

    @Test
    void testObtenerEstadoEntrevistaPorUsuario() {
        EstadoEntrevista estadoEntrevista = EstadoEntrevista.builder().build();

        when(this.entrevistaRepository.obtenerEntrevistaEnProcesoPorUsuario(anyString())).thenReturn(Mono.just(EntrevistaEntity.builder().build()));
        when(this.mapper.mapEntrevistaEntityToEstadoEntrevista(any())).thenReturn(estadoEntrevista);

        Mono<EstadoEntrevista> publisher = this.entrevistaBdDao.obtenerEstadoEntrevistaPorUsuario("any");

        StepVerifier
                .create(publisher)
                .expectNext(estadoEntrevista)
                .verifyComplete();

        verify(this.entrevistaRepository, times(1)).obtenerEntrevistaEnProcesoPorUsuario(anyString());
        verify(this.mapper, times(1)).mapEntrevistaEntityToEstadoEntrevista(any());
    }

    @Test
    void testObtenerEstadoEntrevistaPorId() {
        EstadoEntrevista estadoEntrevista = EstadoEntrevista.builder().build();

        when(this.entrevistaRepository.findById(anyString())).thenReturn(Mono.just(EntrevistaEntity.builder().build()));
        when(this.mapper.mapEntrevistaEntityToEstadoEntrevista(any())).thenReturn(estadoEntrevista);

        Mono<EstadoEntrevista> publisher = this.entrevistaBdDao.obtenerEstadoEntrevistaPorId("any");

        StepVerifier
                .create(publisher)
                .expectNext(estadoEntrevista)
                .verifyComplete();

        verify(this.entrevistaRepository, times(1)).findById(anyString());
        verify(this.mapper, times(1)).mapEntrevistaEntityToEstadoEntrevista(any());
    }

    @Test
    void testActualizarEstadoEntrevista() {
        when(this.entrevistaRepository.actualizarEstadoEntrevistaPorId(anyString(), anyString())).thenReturn(Mono.empty());

        Mono<Void> publisher = this.entrevistaBdDao.actualizarEstadoEntrevista("any", EstadoEntrevistaEnum.FG);

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaRepository, times(1)).actualizarEstadoEntrevistaPorId(anyString(), anyString());
    }

    @Test
    void testConsultarUltimasEntrevistas() {
        Entrevista entrevista = Entrevista.builder().build();

        when(this.entrevistaRepository.findByUsernameOrderByFechaCreacionDesc(anyString()))
                .thenReturn(Flux.just(EntrevistaEntity.builder().build()));
        when(this.mapper.mapOutEntrevistaEntityToEntrevista(any())).thenReturn(entrevista);

        Flux<Entrevista> publisher = this.entrevistaBdDao.consultarUltimasEntrevistas("any");

        StepVerifier
                .create(publisher)
                .expectNext(entrevista)
                .verifyComplete();

        verify(this.entrevistaRepository, times(1)).findByUsernameOrderByFechaCreacionDesc(anyString());
        verify(this.mapper, times(1)).mapOutEntrevistaEntityToEntrevista(any());
    }

    @Test
    void testTerminarEntrevista() {
        EntrevistaEntity entrevistaEntity = EntrevistaEntity.builder().uuid("any").build();

        when(this.entrevistaRepository.findById(anyString())).thenReturn(Mono.just(entrevistaEntity));
        when(this.entrevistaRepository.save(any())).thenReturn(Mono.just(entrevistaEntity));

        Mono<Void> publisher = this.entrevistaBdDao.terminarEntrevista("any", "any");

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaRepository, times(1)).findById(anyString());
        verify(this.entrevistaRepository, times(1)).save(any());
    }
}