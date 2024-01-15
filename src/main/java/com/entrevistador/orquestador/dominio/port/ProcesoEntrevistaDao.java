package com.entrevistador.orquestador.dominio.port;

import com.entrevistador.orquestador.dominio.model.dto.ProcesoEntrevistaDto;

public interface ProcesoEntrevistaDao {

    ProcesoEntrevistaDto crearEvento();

    ProcesoEntrevistaDto obtenerEventoPorId();
    ProcesoEntrevistaDto obtenerEventoPornombre();

}
