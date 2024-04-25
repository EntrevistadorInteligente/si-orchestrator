package com.entrevistador.orquestador.dominio.service;

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


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
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
    void testActualizarEstadoEntrevistaSegunMatch() {
        when(this.entrevistaDao.actualizarEstadoEntrevista(anyString(), anyBoolean())).thenReturn(Mono.empty());

        Mono<Void> publisher = this.actualizarInformacionEntrevistaService.actualizarEstadoEntrevistaSegunMatch(
                "any",
                true
        );

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaDao, times(1)).actualizarEstadoEntrevista(anyString(), anyBoolean());
    }

    @Test
    void testActualizarInformacionEmpresa() {
        String result = "result";
        when(this.entrevistaDao.actualizarEntrevista(any())).thenReturn(Mono.empty());

        Mono<Void> publisher = this.actualizarInformacionEntrevistaService.actualizarInformacionEmpresa(
                "any",
                InformacionEmpresaDto.builder().empresa("any").pais("any").perfil("any").seniority("any").build()
        );

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.entrevistaDao, timeout(1)).actualizarEntrevista(any());
    }
}