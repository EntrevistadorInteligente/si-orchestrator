package com.entrevistador.orquestador.infrastructure.adapter.dao;

import com.entrevistador.orquestador.dominio.excepciones.IdHojaDeVidaRagNuloException;
import com.entrevistador.orquestador.dominio.excepciones.UsernameNoEncontradoException;
import com.entrevistador.orquestador.dominio.model.HojaDeVidaModel;
import com.entrevistador.orquestador.dominio.model.Perfil;
import com.entrevistador.orquestador.infrastructure.adapter.entity.HojaDeVidaEntity;
import com.entrevistador.orquestador.infrastructure.adapter.mapper.HojaDeVidaMapper;
import com.entrevistador.orquestador.infrastructure.adapter.repository.HojaDeVidaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HojaDeVidaBdDaoTest {
    @InjectMocks
    private HojaDeVidaBdDao hojaDeVidaBdDao;
    @Mock
    private HojaDeVidaRepository hojaDeVidaRepository;
    @Mock
    private HojaDeVidaMapper mapper;

    @Test
    void testGuardarHojaDeVida() {
        HojaDeVidaModel hojaDeVidaModel = HojaDeVidaModel.builder().username("any").build();

        when(this.mapper.mapHojaDeVidaModelToHojaDeVidaEntity(any()))
                .thenReturn(HojaDeVidaEntity.builder().username("any").build());
        when(this.hojaDeVidaRepository.actualizarEstadoHojadeVidaPorUsername(anyString(), any())).thenReturn(Mono.empty());
        when(this.hojaDeVidaRepository.save(any())).thenReturn(Mono.just(HojaDeVidaEntity.builder().build()));
        when(this.mapper.mapHojaDeVidaEntityToHojaDeVida(any())).thenReturn(hojaDeVidaModel);

        Mono<HojaDeVidaModel> publisher = this.hojaDeVidaBdDao.guardarHojaDeVida(HojaDeVidaModel.builder().build());

        StepVerifier
                .create(publisher)
                .expectNext(hojaDeVidaModel)
                .verifyComplete();

        verify(this.mapper, times(1)).mapHojaDeVidaModelToHojaDeVidaEntity(any());
        verify(this.hojaDeVidaRepository, times(1)).actualizarEstadoHojadeVidaPorUsername(anyString(), any());
        verify(this.hojaDeVidaRepository, times(1)).save(any());
        verify(this.mapper, times(1)).mapHojaDeVidaEntityToHojaDeVida(any());
    }

    @Test
    void testObtenerIdHojaDeVidaRag() {
        HojaDeVidaEntity hojaDeVidaEntity = HojaDeVidaEntity.builder().idHojaDeVidaRag("any").build();

        when(this.hojaDeVidaRepository.findFirstByUsernameOrderByFechaCreacionDesc(anyString()))
                .thenReturn(Mono.just(hojaDeVidaEntity));

        Mono<String> publisher = this.hojaDeVidaBdDao.obtenerIdHojaDeVidaRag("any");

        StepVerifier
                .create(publisher)
                .expectNext(hojaDeVidaEntity.getIdHojaDeVidaRag())
                .verifyComplete();

        verify(this.hojaDeVidaRepository, times(1)).findFirstByUsernameOrderByFechaCreacionDesc(anyString());
    }

    @Test
    void testObtenerIdHojaDeVidaRag_UsernameNoEncontradoException() {
        when(this.hojaDeVidaRepository.findFirstByUsernameOrderByFechaCreacionDesc(anyString()))
                .thenReturn(Mono.empty());

        Mono<String> publisher = this.hojaDeVidaBdDao.obtenerIdHojaDeVidaRag("any");

        StepVerifier
                .create(publisher)
                .expectError(UsernameNoEncontradoException.class)
                .verify();

        verify(this.hojaDeVidaRepository, times(1)).findFirstByUsernameOrderByFechaCreacionDesc(anyString());
    }

    @Test
    void testObtenerIdHojaDeVidaRag_IdHojaDeVidaRagNuloException() {
        HojaDeVidaEntity hojaDeVidaEntity = HojaDeVidaEntity.builder().idHojaDeVidaRag(null).build();

        when(this.hojaDeVidaRepository.findFirstByUsernameOrderByFechaCreacionDesc(anyString()))
                .thenReturn(Mono.just(hojaDeVidaEntity));

        Mono<String> publisher = this.hojaDeVidaBdDao.obtenerIdHojaDeVidaRag("any");

        StepVerifier
                .create(publisher)
                .expectError(IdHojaDeVidaRagNuloException.class)
                .verify();

        verify(this.hojaDeVidaRepository, times(1)).findFirstByUsernameOrderByFechaCreacionDesc(anyString());
    }

    @Test
    void testObtenerHojaDeVidaPorNombreUsuario() {
        HojaDeVidaEntity hojaDeVidaEntity = HojaDeVidaEntity.builder().username("any").build();
        HojaDeVidaModel hojaDeVidaModel = HojaDeVidaModel.builder().username("any").build();

        when(this.hojaDeVidaRepository.findFirstByUsernameAndEstadoHojaDeVida(anyString(), anyString()))
                .thenReturn(Mono.just(hojaDeVidaEntity));
        when(this.mapper.mapHojaDeVidaEntityToHojaDeVida(any())).thenReturn(hojaDeVidaModel);

        Mono<HojaDeVidaModel> publisher = this.hojaDeVidaBdDao.obtenerHojaDeVidaPorNombreUsuario("any");

        StepVerifier
                .create(publisher)
                .expectNext(hojaDeVidaModel)
                .verifyComplete();

        verify(this.hojaDeVidaRepository, times(1)).findFirstByUsernameAndEstadoHojaDeVida(anyString(), anyString());
        verify(this.mapper, times(1)).mapHojaDeVidaEntityToHojaDeVida(any());
    }

    @Test
    void testActualizarDatosPerfil() {
        HojaDeVidaEntity hojaDeVidaEntity = HojaDeVidaEntity.builder().uuid("any").build();
        HojaDeVidaModel hojaDeVidaModel = HojaDeVidaModel.builder().uuid("any").build();

        when(this.hojaDeVidaRepository.findById(anyString())).thenReturn(Mono.just(hojaDeVidaEntity));
        when(this.hojaDeVidaRepository.save(any())).thenReturn(Mono.just(HojaDeVidaEntity.builder().build()));
        when(this.mapper.mapHojaDeVidaEntityToHojaDeVida(any())).thenReturn(hojaDeVidaModel);

        Mono<HojaDeVidaModel> publisher = this.hojaDeVidaBdDao.actualizarDatosPerfil("any", Perfil.builder().build());

        StepVerifier
                .create(publisher)
                .expectNext(hojaDeVidaModel)
                .verifyComplete();

        verify(this.hojaDeVidaRepository, times(1)).findById(anyString());
        verify(this.hojaDeVidaRepository, times(1)).save(any());
        verify(this.mapper, times(1)).mapHojaDeVidaEntityToHojaDeVida(any());
    }
}