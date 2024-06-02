package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.model.dto.RagsIdsDto;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidadorEventosSimultaneosServiceTest {
    @InjectMocks
    private ValidadorEventosSimultaneosService validadorEventosSimultaneosService;
    @Mock
    private EntrevistaDao entrevistaDao;

    @Test
    void testEjecutar() {

        String result = "result";
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        Map<String, String> map = Map.of(
                "idHojaDeVidaRag", "theTitle",
                "idInformacionEmpresaRag", "theUrl",
                "hojaDeVidaValida", "true"
        );
        RagsIdsDto projection = factory.createProjection(RagsIdsDto.class, map);
        when(this.entrevistaDao.consultarRagsId(any())).thenReturn(Mono.just(projection));

        Mono<RagsIdsDto> publisher = this.validadorEventosSimultaneosService.ejecutar("any");

        StepVerifier
                .create(publisher)
                .expectNext(projection)
                .verifyComplete();
    }

}