package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.dominio.model.IdEntrevista;
import com.entrevistador.orquestador.dominio.model.SoloPerfil;
import com.entrevistador.orquestador.dominio.port.EntrevistaPruebaDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntrevistaPruebaServiceTest {
    @InjectMocks
    private EntrevistaPruebaService entrevistaPruebaService;
    @Mock
    private EntrevistaPruebaDao entrevistaPruebaDao;

    @Test
    void testGetPerfiles() {
        SoloPerfil soloPerfil = SoloPerfil.builder().build();

        when(this.entrevistaPruebaDao.getPerfil()).thenReturn(Flux.just(soloPerfil));

        Flux<SoloPerfil> publisher = this.entrevistaPruebaService.getPerfiles();

        StepVerifier
                .create(publisher)
                .expectNext(soloPerfil)
                .verifyComplete();

        verify(this.entrevistaPruebaDao, times(1)).getPerfil();
    }

    @Test
    void testGetIdEntrevista() {
        IdEntrevista idEntrevista = IdEntrevista.builder().build();

        when(this.entrevistaPruebaDao.getIdEntrevistaByPerfil(anyString())).thenReturn(Mono.just(idEntrevista));

        Mono<IdEntrevista> publisher = this.entrevistaPruebaService.getIdEntrevista("any");

        StepVerifier
                .create(publisher)
                .expectNext(idEntrevista)
                .verifyComplete();

        verify(this.entrevistaPruebaDao, times(1)).getIdEntrevistaByPerfil(anyString());
    }
}