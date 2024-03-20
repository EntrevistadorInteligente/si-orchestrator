package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.excepciones.ActualizarEstadoEntrevistaException;
import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.enums.EstadoProcesoEnum;
import com.entrevistador.orquestador.dominio.model.enums.FuenteEnum;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActualizarEstadoProcesoEntrevistaServiceTest {

    private ProcesoEntrevistaDto procesoEntrevistaDtoVacio = ProcesoEntrevistaDto.builder()
            .uuid("")
            .fechaYHora(null)
            .estado(null)
            .fuente(null)
            .error("")
            .build();

    private ProcesoEntrevistaDto procesoEntrevistaDtoActualizar = ProcesoEntrevistaDto.builder()
            .fechaYHora(new Date())
            .estado(EstadoProcesoEnum.AC)
            .fuente(FuenteEnum.ANALIZADOR)
            .error("")
            .build();

    @Test
    public void dtoVacioActualizarTest() {
        assertThrows(ActualizarEstadoEntrevistaException.class, () ->
                this.procesoEntrevistaDtoActualizar.actualizar(procesoEntrevistaDtoVacio));
    }

    @Test
    public void dtoActualizarTest() {
        assertTrue(this.procesoEntrevistaDtoVacio.actualizar(procesoEntrevistaDtoActualizar));
    }

}