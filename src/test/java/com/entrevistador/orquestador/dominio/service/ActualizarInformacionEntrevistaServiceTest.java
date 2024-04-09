package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.InformacionEmpresaDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActualizarInformacionEntrevistaServiceTest {
    @InjectMocks
    private ActualizarInformacionEntrevistaService actualizarInformacionEntrevistaService;
    @Mock
    private EntrevistaDao entrevistaDao;

    @Test
    void testActualizarHojaDeVida() {
        String result = "result";
        when(this.entrevistaDao.actualizarEntrevista(any())).thenReturn(Mono.just(result));

        Mono<String> publisher = this.actualizarInformacionEntrevistaService.actualizarHojaDeVida(
                "any",
                HojaDeVidaDto.builder().build()
        );

        StepVerifier
                .create(publisher)
                .expectNext(result)
                .verifyComplete();

        verify(this.entrevistaDao, times(1)).actualizarEntrevista(any());
    }

    @Test
    void testActualizarInformacionEmpresa() {
        String result = "result";
        when(this.entrevistaDao.actualizarEntrevista(any())).thenReturn(Mono.just(result));

        Mono<String> publisher = this.actualizarInformacionEntrevistaService.actualizarInformacionEmpresa(
                "any",
                InformacionEmpresaDto.builder().empresa("any").pais("any").perfil("any").seniority("any").build()
        );

        StepVerifier
                .create(publisher)
                .expectNext(result)
                .verifyComplete();

        verify(this.entrevistaDao, timeout(1)).actualizarEntrevista(any());
    }
}